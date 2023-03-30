package example.app.entities.users;

import example.app.exceptions.EmptyArgumentException;
import example.app.exceptions.InvalidIdException;
import example.app.exceptions.NullArgumentException;

public class FacultyUser extends User {
    public FacultyUser(String username, String password, String firstName, String lastName, String email)
            throws NullArgumentException, EmptyArgumentException, InvalidIdException {
        super(username, password, firstName, lastName, email);
    }
}
