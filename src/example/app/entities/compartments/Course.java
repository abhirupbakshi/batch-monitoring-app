package example.app.entities.compartments;

import example.app.entities.exceptions.EmptyArgumentException;
import example.app.entities.exceptions.NullArgumentException;
import example.app.entities.FileDatabaseCompatible;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

/**
 * <h1>Class Course</h1>
 * This class represents a course. After the instantiation, the fields cannot be changed.
 * For two courses to be equal, they must have the same name, description and code
 */
public class Course implements Serializable, FileDatabaseCompatible {
    private final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private final UUID id;
    private final String name;
    private final String description;
    private final String code;
    private final LocalDateTime courseCreationDate;

    /**
     * Getter for the id
     * @return
     */
    public UUID getId() {
        return id;
    }

    /**
     * Getter for the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the description
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for the Course code
     * @return the code of the Course code
     */
    public String getCode() {
        return code;
    }

    /**
     * Getter for the creation date
     * @return the creation date
     */
    public LocalDateTime getCreationDate() {
        return LocalDateTime.parse(courseCreationDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
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

    @Override
    public String toString() {
        return  "Name: " + this.getName() + "\n" +
                "Description: " + this.getDescription() + "\n" +
                "Course Code: " + this.getCode();
    }

    /**
     * A private method that checks the validity of a value based on whether it is null, empty string or not.
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

    public Course(String name, String description, String code)
            throws NullArgumentException, EmptyArgumentException{
        this.checkValidity(name, "name");
        this.checkValidity(description, "description");
        this.checkValidity(code, "code");

        this.name = name;
        this.description = description;
        this.code = code;
        this.id = UUID.randomUUID();
        this.courseCreationDate = LocalDateTime.now();
    }
}
