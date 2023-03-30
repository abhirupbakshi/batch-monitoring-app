package example.app.entities.users;

import example.app.exceptions.EmptyArgumentException;
import example.app.exceptions.InvalidIdException;
import example.app.exceptions.NullArgumentException;
import example.app.utility.Encrypt;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

class User {
    final private UUID id;
    private String username;
    private byte[] password;
    private String firstName;
    private String lastName;
    private String email;
    private final LocalDate accountCreationDate;
    
    private void checkId(String id)
            throws NullArgumentException, EmptyArgumentException, InvalidIdException {
        if(id == null)
            throw new NullArgumentException("Cannot set the value of \"id\" in class \"User\" to null");
        if(id.isEmpty())
            throw new EmptyArgumentException("Cannot set the value of \"id\" in class \"User\" to an empty string");
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException exception) {
            throw new InvalidIdException("Cannot set the value of \"id\" in class \"User\" to an invalid UUID", exception);
        }
    }

    public UUID getId() { return id; }

    public String getUsername() { return username; }

    public void setUsername(String username)
            throws NullArgumentException, EmptyArgumentException {
        if(username == null)
            throw new NullArgumentException("Cannot set the value of \"username\" in class \"User\" to null");
        if(username.isEmpty())
            throw new EmptyArgumentException("Cannot set the value of \"username\" in class \"User\" to an empty string");

        this.username = username;
    }

    public void setPassword(String password)
            throws NullArgumentException, EmptyArgumentException {
        if(password == null)
            throw new NullArgumentException("Cannot set the value of \"password\" in class \"User\" to null");
        if(password.isEmpty())
            throw new EmptyArgumentException("Cannot set the value of \"password\" in class \"User\" to an empty string");

        this.password = Encrypt.fromString(password);
    }

    public void setPassword(byte[] password)
            throws NullArgumentException, EmptyArgumentException {
        if(password == null)
            throw new NullArgumentException("Cannot set the value of \"password\" in class \"User\" to null");
        if(password.length == 0)
            throw new EmptyArgumentException("Cannot set the value of \"password\" in class \"User\" to an empty byte array");

        this.password = password;
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName)
            throws NullArgumentException, EmptyArgumentException {
        if(firstName == null)
            throw new NullArgumentException("Cannot set the value of \"firstName\" in class \"User\" to null");
        if(firstName.isEmpty())
            throw new EmptyArgumentException("Cannot set the value of \"firstName\" in class \"User\" to an empty string");

        this.firstName = firstName;
    }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName)
            throws NullArgumentException, EmptyArgumentException {
        if(lastName == null)
            throw new NullArgumentException("Cannot set the value of \"lastName\" in class \"User\" to null");
        if(lastName.isEmpty())
            throw new EmptyArgumentException("Cannot set the value of \"lastName\" in class \"User\" to an empty string");

        this.lastName = lastName;
    }

    public String getEmail() { return email; }

    public void setEmail(String email)
            throws NullArgumentException, EmptyArgumentException {
        if(email == null)
            throw new NullArgumentException("Cannot set the value of \"email\" in class \"User\" to null");
        if(email.isEmpty())
            throw new EmptyArgumentException("Cannot set the value of \"email\" in class \"User\" to an empty string");

        this.email = email;
    }

    public LocalDate getAccountCreationDate() {
        return accountCreationDate;
    }

    public boolean usernameEquals(String username) { return this.username.equals(username); }

    public boolean passwordEquals(String password) {
        return Arrays.equals(this.password, Encrypt.fromString(password));
    }

    public boolean credentialsEquals(String username, String password) {
        return this.usernameEquals(username) && this.passwordEquals(password);
    }

    private User(String username, String firstName, String lastName, String email, String... ids)
            throws NullArgumentException, EmptyArgumentException, InvalidIdException {
        if(ids.length == 0) this.id = UUID.randomUUID();
        else {
            checkId(ids[0]);
            this.id = UUID.fromString(ids[0]);
        }
        this.setUsername(username);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.accountCreationDate = LocalDate.now();
    }

    User(String username, String password, String firstName, String lastName, String email)
            throws NullArgumentException, EmptyArgumentException, InvalidIdException {
        this(username, firstName, lastName, email);
        this.setPassword(password);
    }

    User(String username, byte[] password, String firstName, String lastName, String email)
            throws NullArgumentException, EmptyArgumentException, InvalidIdException {
        this(username, firstName, lastName, email);
        this.setPassword(password);
    }

    User(String id, String username, String password, String firstName, String lastName, String email)
            throws NullArgumentException, EmptyArgumentException, InvalidIdException {
        this(username, firstName, lastName, email, id);
        this.setPassword(password);
    }

    User(String id, String username, byte[] password, String firstName, String lastName, String email)
            throws NullArgumentException, EmptyArgumentException, InvalidIdException {
        this(username, firstName, lastName, email, id);
        this.setPassword(password);
    }
}
