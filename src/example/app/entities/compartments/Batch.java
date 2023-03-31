package example.app.entities.compartments;

import example.app.exceptions.EmptyArgumentException;
import example.app.exceptions.InvalidDateException;
import example.app.exceptions.NullArgumentException;
import example.app.utility.CheckDate;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

/**
 * <h1>Class Batch</h1>
 * A Batch represent a batch of a course. Every batch has an UUID, a name, a course, a description,
 * a start date and an end date, a duration of days, a creation date and list of faculties assigned
 * to it. The id is assigned automatically and cannot be changed later.
 */
public class Batch implements Serializable {
    private final UUID id;
    private String name;
    private Course course;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private long durationDays;
    private final LocalDate batchCreationDate;
    private final HashMap<UUID, Faculty> assignedFaculties = new HashMap<>();

    /**
     * @return the id of the batch
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return the name of the batch
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name of the batch
     * @throws NullArgumentException if the name is null
     * @throws EmptyArgumentException if the name is empty
     */
    public void setName(String name)
            throws NullArgumentException, EmptyArgumentException {
        if(name == null)
            throw new NullArgumentException("Cannot set the value of \"name\" in class \"Batch\" to null");
        if(name.isEmpty())
            throw new EmptyArgumentException("Cannot set the value of \"name\" in class \"Batch\" to an empty string");

        this.name = name;
    }

    /**
     * @return the course of the batch
     */
    public Course getCourse() {
        return course;
    }

    /**
     * @param course a course that needs to added to the batch
     * @throws NullArgumentException if the course is null
     */
    public void setCourse(Course course) throws NullArgumentException {
        if(course == null)
            throw new NullArgumentException("Cannot set the value of \"course\" in class \"Batch\" to null");

        this.course = course;
    }

    /**
     * @return the description of the batch
     */
    public String getDescription() {
        return description;
    }

    /**
     * User can set an empty description
     * @param description the description of the batch to be replaced with
     * @throws NullArgumentException if the description is null
     */
    public void setDescription(String description)
            throws NullArgumentException {
        if(description == null)
            throw new NullArgumentException("Cannot set the value of \"description\" in class \"Batch\" to null");

        this.description = description;
    }

    /**
     * @param startDate the start date of the batch
     * @param endDate the end date of the batch
     * @throws NullArgumentException if the startDate or endDate is null
     * @throws InvalidDateException if the startDate is after the endDate
     * @throws InvalidDateException if the startDate is equal to the endDate
     */
    public void setDate(LocalDate startDate, LocalDate endDate)
            throws NullArgumentException, InvalidDateException {
        if(startDate == null)
            throw new NullArgumentException("Cannot set the value of \"startDate\" in class \"Batch\" to null");
        if(endDate == null)
            throw new NullArgumentException("Cannot set the value of \"endDate\" in class \"Batch\" to null");
        if(startDate.isAfter(endDate))
            throw new InvalidDateException("Cannot set the value of \"startDate\" in class \"Batch\" to a date after \"endDate\"");
        if(startDate.isEqual(endDate))
            throw new InvalidDateException("Cannot set the value of \"startDate\" in class \"Batch\" to a date equal to \"endDate\"");

        this.startDate = startDate;
        this.endDate = endDate;
        durationDays = ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * @param startDate the start date of the batch
     * @param endDate the end date of the batch
     * @throws NullArgumentException if the startDate or endDate is null
     * @throws InvalidDateException if the startDate or endDate in wrong format
     * @throws InvalidDateException if the startDate is after the endDate
     * @throws InvalidDateException if the startDate is equal to the endDate
     */
    public void setDate(String startDate, String endDate)
            throws InvalidDateException, NullArgumentException {
        LocalDate startDateLocal;
        LocalDate endDateLocal;

        try {
            startDateLocal = CheckDate.isValidAndConvert(startDate);
        }
        catch (NullArgumentException exception) {
            throw new NullArgumentException("Cannot set the value of \"startDate\" in class \"Batch\" to null");
        }
        catch (DateTimeParseException exception) {
            throw new InvalidDateException("Cannot set the value of \"startDate\" in class \"Batch\". Wrong format", exception);
        }

        try {
            endDateLocal = CheckDate.isValidAndConvert(endDate);
        }
        catch (NullArgumentException exception) {
            throw new NullArgumentException("Cannot set the value of \"endDate\" in class \"Batch\" to null");
        }
        catch (DateTimeParseException exception) {
            throw new InvalidDateException("Cannot set the value of \"endDate\" in class \"Batch\". Wrong format", exception);
        }

        setDate(startDateLocal, endDateLocal);
    }

    /**
     * @param startDate the start date of the batch
     * @param durationDays the duration of the batch
     * @throws NullArgumentException if the startDate is null
     * @throws InvalidDateException if the durationDays is negative or zero
     */
    public void setDate(LocalDate startDate, long durationDays)
            throws NullArgumentException, InvalidDateException {
        if(startDate == null)
            throw new NullArgumentException("Cannot set the value of \"startDate\" in class \"Batch\" to null");
        if(durationDays <= 0)
            throw new InvalidDateException("Cannot set the value of \"durationDays\" in class \"Batch\" to a negative or zero value");

        setDate(startDate, startDate.plusDays(durationDays));
    }

    /**
     * @param startDate the start date of the batch
     * @param durationDays the duration of the batch
     * @throws NullArgumentException if the startDate is null
     * @throws InvalidDateException if the startDate is in wrong format
     * @throws InvalidDateException if the durationDays is negative or zero
     */
    public void setDate(String startDate, long durationDays)
            throws NullArgumentException, InvalidDateException {
        try {
            setDate(CheckDate.isValidAndConvert(startDate), durationDays);
        }
        catch (NullArgumentException exception) {
            throw new NullArgumentException("Cannot set the value of \"startDate\" in class \"Batch\" to null");
        }
        catch (DateTimeParseException exception) {
            throw new InvalidDateException("Cannot set the value of \"startDate\" in class \"Batch\". Wrong format", exception);
        }
    }

    /**
     * @return the start date of the batch
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the start date of the batch
     * @throws InvalidDateException if the startDate is after the endDate
     * @throws NullArgumentException if the startDate is null
     */
    public void setStartDate(LocalDate startDate)
            throws InvalidDateException, NullArgumentException {
        if(startDate == null)
            throw new NullArgumentException("Cannot set the value of \"startDate\" in class \"Batch\" to null");

        setDate(startDate, this.endDate);
    }

    /**
     * @param startDate the start date of the batch
     * @throws InvalidDateException if the startDate is after the endDate
     * @throws InvalidDateException if the startDate is in wrong format
     * @throws NullArgumentException if the startDate is null
     */
    public void setStartDate(String startDate)
            throws InvalidDateException, NullArgumentException {
        try {
            setDate(CheckDate.isValidAndConvert(startDate), this.endDate);
        }
        catch (NullArgumentException exception) {
            throw new NullArgumentException("Cannot set the value of \"startDate\" in class \"Batch\" to null");
        }
        catch (DateTimeParseException exception) {
            throw new InvalidDateException("Cannot set the value of \"startDate\" in class \"Batch\". Wrong format", exception);
        }
    }

    /**
     * @return the end date of the batch
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the end date of the batch
     * @throws InvalidDateException if the endDate is before the startDate
     * @throws NullArgumentException if the endDate is null
     */
    public void setEndDate(LocalDate endDate)
            throws InvalidDateException, NullArgumentException {
        if(endDate == null)
            throw new NullArgumentException("Cannot set the value of \"endDate\" in class \"Batch\" to null");

        setDate(this.startDate, endDate);
    }

    /**
     * @param endDate the end date of the batch
     * @throws InvalidDateException if the endDate is before the startDate
     * @throws InvalidDateException if the endDate is in wrong format
     * @throws NullArgumentException if the endDate is null
     */
    public void setEndDate(String endDate)
            throws InvalidDateException, NullArgumentException {
        try {
            setDate(this.startDate, CheckDate.isValidAndConvert(endDate));
        }
        catch (NullArgumentException exception) {
            throw new NullArgumentException("Cannot set the value of \"endDate\" in class \"Batch\" to null");
        }
        catch (DateTimeParseException exception) {
            throw new InvalidDateException("Cannot set the value of \"endDate\" in class \"Batch\". Wrong format", exception);
        }
    }

    /**
     * @return the duration of the batch
     */
    public long getDurationDays() {
        return durationDays;
    }

    /**
     * @param durationDays the duration of the batch
     * @throws InvalidDateException if the durationDays is negative or zero
     * @throws NullArgumentException if the durationDays is null
     */
    public void setDurationDays(long durationDays)
            throws InvalidDateException, NullArgumentException {
        setDate(this.startDate, durationDays);
    }

    /**
     * @return A list of the assigned faculties
     */
    public HashMap<UUID, Faculty> getAssignedFaculties() {
        return assignedFaculties;
    }

    /**
     * @param faculties the faculties to add to the batch
     * @throws NullArgumentException if any given faculty is null and does not add any faculty to the batch
     */
    public void addFaculties(Faculty... faculties) throws NullArgumentException {
        for(Faculty faculty : faculties) {
            if(faculty == null)
                throw new NullArgumentException("Cannot add null to \"assignedFaculties\" in class \"Batch\"");
        }

        for(Faculty faculty : faculties) {
            assignedFaculties.put(faculty.getId(), faculty);
        }
    }

    /**
     * @param faculties the faculties to remove from the batch
     */
    public void removeFaculties(Faculty... faculties) {
        for(Faculty faculty : faculties) {
            assignedFaculties.remove(faculty.getId());
        }
    }

    /**
     * @return the batch creation date
     */
    public LocalDate getBatchCreationDate() {
        return batchCreationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Batch batch)) return false;
        return getDurationDays() == batch.getDurationDays() &&
                getName().equals(batch.getName()) &&
                getCourse().equals(batch.getCourse()) &&
                getDescription().equals(batch.getDescription()) &&
                getStartDate().equals(batch.getStartDate()) &&
                getEndDate().equals(batch.getEndDate()) &&
                getBatchCreationDate().equals(batch.getBatchCreationDate()) &&
                getAssignedFaculties().equals(batch.getAssignedFaculties());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getName(),
                getCourse(),
                getDescription(),
                getStartDate(),
                getEndDate(),
                getDurationDays(),
                getBatchCreationDate(),
                getAssignedFaculties()
        );
    }

    /**
     * @param name the name of the batch
     * @param course the course of the batch
     * @param description the description of the batch
     * @param startDate the start date of the batch
     * @param endDate the end date of the batch
     * @throws NullArgumentException for any reason mentioned in the setter methods
     * @throws EmptyArgumentException for any reason mentioned in the setter methods
     * @throws InvalidDateException for any reason mentioned in the setter methods
     */
    public Batch(String name, Course course, String description, LocalDate startDate, LocalDate endDate)
            throws NullArgumentException, EmptyArgumentException, InvalidDateException {
        this.id = UUID.randomUUID();
        this.setName(name);
        this.setCourse(course);
        this.setDescription(description);
        this.setDate(startDate, endDate);
        this.setDurationDays(ChronoUnit.DAYS.between(startDate, endDate));
        this.batchCreationDate = LocalDate.now();
    }

    /**
     * @param name the name of the batch
     * @param course the course of the batch
     * @param description the description of the batch
     * @param startDate the start date of the batch
     * @param durationDays the duration of the batch
     * @throws NullArgumentException for any reason mentioned in the setter methods
     * @throws EmptyArgumentException for any reason mentioned in the setter methods
     * @throws InvalidDateException for any reason mentioned in the setter methods
     */
    public Batch(String name, Course course, String description, LocalDate startDate, long durationDays)
            throws NullArgumentException, EmptyArgumentException, InvalidDateException {
        this.id = UUID.randomUUID();
        this.setName(name);
        this.setCourse(course);
        this.setDescription(description);
        this.setDate(startDate, durationDays);
        this.setEndDate(startDate.plusDays(durationDays));
        this.batchCreationDate = LocalDate.now();
    }

    /**
     * @param name the name of the batch
     * @param course the course of the batch
     * @param description the description of the batch
     * @param startDate the start date of the batch
     * @param endDate the end date of the batch
     * @throws NullArgumentException for any reason mentioned in the setter methods
     * @throws EmptyArgumentException for any reason mentioned in the setter methods
     * @throws InvalidDateException for any reason mentioned in the setter methods
     */
    public Batch(String name, Course course, String description, String startDate, String endDate)
            throws NullArgumentException, EmptyArgumentException, InvalidDateException {
        this.id = UUID.randomUUID();
        this.setName(name);
        this.setCourse(course);
        this.setDescription(description);
        this.setDate(startDate, endDate);
        this.setDurationDays(ChronoUnit.DAYS.between(this.startDate, this.endDate));
        this.batchCreationDate = LocalDate.now();
    }

    /**
     * @param name the name of the batch
     * @param course the course of the batch
     * @param description the description of the batch
     * @param startDate the start date of the batch
     * @param durationDays the duration of the batch
     * @throws NullArgumentException for any reason mentioned in the setter methods
     * @throws EmptyArgumentException for any reason mentioned in the setter methods
     * @throws InvalidDateException for any reason mentioned in the setter methods
     */
    public Batch(String name, Course course, String description, String startDate, long durationDays)
            throws NullArgumentException, EmptyArgumentException, InvalidDateException {
        this.id = UUID.randomUUID();
        this.setName(name);
        this.setCourse(course);
        this.setDescription(description);
        this.setDate(startDate, durationDays);
        this.setEndDate(this.startDate.plusDays(durationDays));
        this.batchCreationDate = LocalDate.now();
    }
}
