package example.app.entities.compartments;

import example.app.exceptions.EmptyArgumentException;
import example.app.exceptions.NullArgumentException;

import java.io.Serializable;
import java.util.Objects;

/**
 * <h1>Class Course</h1>
 * This class represents a course.
 */
public class Course implements Serializable {
    private final String name;
    private final String description;
    private final String code;

    /**
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return the code of the Course
     */
    public String getCode() {
        return code;
    }

    /**
     * A private method that checks the validity of the value based on whether it is null, empty string or not.
     * @param value the value to be checked
     * @param fieldName the name of the field to be added to the exception
     * @throws NullArgumentException if the value is null
     * @throws EmptyArgumentException if the value is empty
     */
    private void checkValidity(String value, String fieldName)
            throws NullArgumentException, EmptyArgumentException {
        if(value == null)
            throw new NullArgumentException("Cannot set the value of \"" + fieldName + "\" in class \"Course\" to null");
        if(value.isEmpty())
            throw new EmptyArgumentException("Cannot set the value of \"" + fieldName + "\" in class \"Course\" to an empty string");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return getName().equals(course.getName()) && getDescription().equals(course.getDescription()) && getCode().equals(course.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getCode());
    }

    /**
     * The constructor of the class.
     * @param name
     * @param description
     * @param code
     * @throws NullArgumentException if any value is null
     * @throws EmptyArgumentException if any value is an empty string
     */
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
