package example.app.entities.compartments;

import example.app.exceptions.EmptyArgumentException;
import example.app.exceptions.NullArgumentException;

public class Course {
    private final String name;
    private final String description;
    private final String code;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    private void checkValidity(String value, String fieldName)
            throws NullArgumentException, EmptyArgumentException {
        if(value == null)
            throw new NullArgumentException("Cannot set the value of \"" + fieldName + "\" in class \"Course\" to null");
        if(value.isEmpty())
            throw new EmptyArgumentException("Cannot set the value of \"" + fieldName + "\" in class \"Course\" to an empty string");
    }

    public Course(String name, String description, String code)
            throws NullArgumentException, EmptyArgumentException{
        this.checkValidity(name, "name");
        this.checkValidity(description, "description");
        this.checkValidity(code, "code");

        this.name = name;
        this.description = description;
        this.code = code;
    }
}
