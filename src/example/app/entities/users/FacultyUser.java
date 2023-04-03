package example.app.entities.users;

import example.app.entities.exceptions.EmptyArgumentException;
import example.app.entities.exceptions.NullArgumentException;

/**
 * <h1>Class FacultyUser</h1>
 * This class extends User class and represents a faculty user.
 */
public class FacultyUser extends User {
    public FacultyUser(String username, String password, String firstName, String lastName, String email)
            throws NullArgumentException, EmptyArgumentException {
        super(username, password, firstName, lastName, email);
    }
}
