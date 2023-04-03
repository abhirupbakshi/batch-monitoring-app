package example.app.entities.compartments;

import example.app.entities.FileDatabaseCompatible;
import example.app.entities.exceptions.EmptyArgumentException;
import example.app.entities.exceptions.InvalidDateException;
import example.app.entities.exceptions.NullArgumentException;
import example.app.entitylist.FacultyList;
import example.app.entitylist.exceptions.DuplicatePresentException;
import example.app.filedatabase.Exceptions.CannotCreateFileException;
import example.app.filedatabase.Exceptions.FileDatabaseConfigFileException;
import example.app.filedatabase.Exceptions.FileDatabaseInternalException;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * <h1>Class Batch</h1>
 * This class represent a batch.
 * <ul>
 *     <li>Id: UUID v4 that is immutable after assignment</li>
 *     <li>Name: Name of the batch</li>
 *     <li>Description: Description of the batch (optional)</li>
 *     <li>Start Date and End Date: Start date and end date of the batch. Start date always needs to come before the end date</li>
 *     <li>Duration: Duration of the batch in days. Duration have to be greater than zero</li>
 *     <li>Course: Course that has been assigned to the batch</li>
 *     <li>Creation Date: Date and time on which the batch was created. It's immutable</li>
 *     <li>Assigned Faculties: A list of faculties that has been assigned to the batch</li>
 *     <li>For two batches to be same, only name, start and end date, course needs to be equal</li>
 * </ul>
 */
public class Batch implements Serializable, FileDatabaseCompatible {
    private final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private final UUID id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private long durationDays;
    private Course course;
    private final LocalDateTime batchCreationDate;
    private final TreeMap<LocalDateTime, UUID> assignedFaculties = new TreeMap<>();

    /**
     * Getter for the id of the batch
     * @return the id of the batch
     */
    public UUID getId() {
        return id;
    }

    /**
     * Getter for the name of the batch
     * @return the name of the batch
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the batch
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
     * Getter for the course of the batch
     * @return the course of the batch
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Setter for the course of the batch
     * @param course a course that needs to added to the batch
     * @throws NullArgumentException if the course is null
     */
    public void setCourse(Course course) throws NullArgumentException {
        if(course == null)
            throw new NullArgumentException("Cannot set the value of \"course\" in class \"Batch\" to null");

        this.course = course;
    }

    /**
     * Getter for the description of the batch
     * @return the description of the batch
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the description of the batch. Description can be an empty string as it's optional
     * @param description the description of the batch
     * @throws NullArgumentException if the description is null
     */
    public void setDescription(String description)
            throws NullArgumentException {
        if(description == null)
            throw new NullArgumentException("Cannot set the value of \"description\" in class \"Batch\" to null");

        this.description = description;
    }

    /**
     * this method checks if a date in String format is in correct format and convert it to a LocalDateTime
     * @param date the date in String format
     * @return the date in LocalDateTime format
     * @throws NullArgumentException if the date is null
     * @throws DateTimeParseException if the date is not in correct format
     */
    private LocalDateTime isValidAndConvert(String date)
            throws NullArgumentException, DateTimeParseException {
        if(date == null)
            throw new NullArgumentException();

        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    /**
     * Setter for the start and end date of the batch
     * @param startDate the start date of the batch
     * @param endDate the end date of the batch
     * @throws NullArgumentException if the startDate or endDate is null
     * @throws InvalidDateException if the startDate is after or equal to the endDate
     */
    public void setDate(LocalDateTime startDate, LocalDateTime endDate)
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
     * Setter for the start and end date of the batch
     * @param startDate the start date of the batch
     * @param endDate the end date of the batch
     * @throws NullArgumentException if the startDate or endDate is null
     * @throws InvalidDateException if the startDate or endDate in wrong format or if the startDate is after or equal to the endDate
     */
    public void setDate(String startDate, String endDate)
            throws InvalidDateException, NullArgumentException {
        LocalDateTime startDateLocal;
        LocalDateTime endDateLocal;

        try {
            startDateLocal = isValidAndConvert(startDate);
        }
        catch (NullArgumentException exception) {
            throw new NullArgumentException("Cannot set the value of \"startDate\" in class \"Batch\" to null");
        }
        catch (DateTimeParseException exception) {
            throw new InvalidDateException("Provided \"startDate\" in class \"Batch\" is in Wrong format", exception);
        }

        try {
            endDateLocal = isValidAndConvert(endDate);
        }
        catch (NullArgumentException exception) {
            throw new NullArgumentException("Cannot set the value of \"endDate\" in class \"Batch\" to null");
        }
        catch (DateTimeParseException exception) {
            throw new InvalidDateException("Provided \"endDate\" in class \"Batch\" is in Wrong format", exception);
        }

        setDate(startDateLocal, endDateLocal);
    }

    /**
     * Setter for the start and duration of the batch
     * @param startDate the start date of the batch
     * @param durationDays the duration of the batch
     * @throws NullArgumentException if the startDate is null
     * @throws InvalidDateException if the durationDays is negative or zero
     */
    public void setDate(LocalDateTime startDate, long durationDays)
            throws NullArgumentException, InvalidDateException {
        if(startDate == null)
            throw new NullArgumentException("Cannot set the value of \"startDate\" in class \"Batch\" to null");
        if(durationDays <= 0)
            throw new InvalidDateException("Cannot set the value of \"durationDays\" in class \"Batch\" to a negative or zero value");

        setDate(startDate, startDate.plusDays(durationDays));
    }

    /**
     * Setter for the start and duration of the batch
     * @param startDate the start date of the batch
     * @param durationDays the duration of the batch
     * @throws NullArgumentException if the startDate is null
     * @throws InvalidDateException if the startDate is in wrong format or if the durationDays is negative or zero
     */
    public void setDate(String startDate, long durationDays)
            throws NullArgumentException, InvalidDateException {
        try {
            setDate(isValidAndConvert(startDate), durationDays);
        }
        catch (NullArgumentException exception) {
            throw new NullArgumentException("Cannot set the value of \"startDate\" in class \"Batch\" to null");
        }
        catch (DateTimeParseException exception) {
            throw new InvalidDateException("Provided \"startDate\" in class \"Batch\" is in Wrong format\"", exception);
        }
    }

    /**
     * Getter for the start date of the batch
     * @return the start date of the batch
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Setter for the start date of the batch
     * @param startDate the start date of the batch
     * @throws InvalidDateException if the startDate is after the endDate
     * @throws NullArgumentException if the startDate is null
     */
    public void setStartDate(LocalDateTime startDate)
            throws InvalidDateException, NullArgumentException {
        if(startDate == null)
            throw new NullArgumentException("Cannot set the value of \"startDate\" in class \"Batch\" to null");

        setDate(startDate, this.endDate);
    }

    /**
     * Setter for the start date of the batch
     * @param startDate the start date of the batch
     * @throws InvalidDateException if the startDate is in wrong format or if it is after the or equal to the endDate
     * @throws NullArgumentException if the startDate is null
     */
    public void setStartDate(String startDate)
            throws InvalidDateException, NullArgumentException {
        try {
            setDate(isValidAndConvert(startDate), this.endDate);
        }
        catch (NullArgumentException exception) {
            throw new NullArgumentException("Cannot set the value of \"startDate\" in class \"Batch\" to null");
        }
        catch (DateTimeParseException exception) {
            throw new InvalidDateException("Provided \"startDate\" in class \"Batch\" is in Wrong format\"", exception);
        }
    }

    /**
     * Getter for the end date of the batch
     * @return the end date of the batch
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Setter for the end date of the batch
     * @param endDate the end date of the batch
     * @throws InvalidDateException if the endDate is before or equal to the startDate
     * @throws NullArgumentException if the endDate is null
     */
    public void setEndDate(LocalDateTime endDate)
            throws InvalidDateException, NullArgumentException {
        if(endDate == null)
            throw new NullArgumentException("Cannot set the value of \"endDate\" in class \"Batch\" to null");

        setDate(this.startDate, endDate);
    }

    /**
     * Setter for the end date of the batch
     * @param endDate the end date of the batch
     * @throws InvalidDateException if the endDate is before or equal to the startDate or if the endDate is in wrong format
     * @throws NullArgumentException if the endDate is null
     */
    public void setEndDate(String endDate)
            throws InvalidDateException, NullArgumentException {
        try {
            setDate(this.startDate, isValidAndConvert(endDate));
        }
        catch (NullArgumentException exception) {
            throw new NullArgumentException("Cannot set the value of \"endDate\" in class \"Batch\" to null");
        }
        catch (DateTimeParseException exception) {
            throw new InvalidDateException("Provided \"endDate\" in class \"Batch\" is in Wrong format\"", exception);
        }
    }

    /**
     * Getter for the duration of the batch
     * @return the duration of the batch
     */
    public long getDurationDays() {
        return durationDays;
    }

    /**
     * Setter for the duration of the batch
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
    public TreeMap<LocalDateTime, UUID> getAssignedFaculties() {
        return assignedFaculties;
    }

    /**
     * This method assigns an array of faculties to the batch
     * @param faculties the faculties to add to the batch
     * @throws NullArgumentException if any given faculty is null and in that case does not add any faculty to the batch
     * @throws DuplicatePresentException
     * @throws CannotCreateFileException
     * @throws FileDatabaseConfigFileException
     * @throws FileDatabaseInternalException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void addFaculties(Faculty... faculties)
            throws NullArgumentException, DuplicatePresentException, CannotCreateFileException,
            FileDatabaseConfigFileException, FileDatabaseInternalException, IOException,
            ClassNotFoundException {
        for(Faculty faculty : faculties) {
            if(faculty == null)
                throw new NullArgumentException("Cannot add null to \"assignedFaculties\" in class \"Batch\"");

            for(LocalDateTime key : assignedFaculties.keySet()) {
                if(new FacultyList().get(assignedFaculties.get(key)).equals(faculty))
                    throw new DuplicatePresentException("Faculty with id " + faculty.getId() + " is already assigned to the batch");
            }
        }

        for(Faculty faculty : faculties) {
            assignedFaculties.put(faculty.getCreationDate(), faculty.getId());
        }
    }

    /**
     * This method removes an array of faculties from the batch
     * @param faculties the faculties to remove from the batch
     */
    public void removeFaculties(Faculty... faculties) {
        for(Faculty faculty : faculties) {
            assignedFaculties.remove(faculty.getCreationDate());
        }
    }

    /**
     * Getter for the batch creation date
     * @return the batch creation date
     */
    public LocalDateTime getCreationDate() {
        return LocalDateTime.parse(batchCreationDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Batch batch)) return false;
        return  getName().equals(batch.getName()) &&
                getStartDate().equals(batch.getStartDate()) &&
                getEndDate().equals(batch.getEndDate()) &&
                getCourse().equals(batch.getCourse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getName(),
                getStartDate(),
                getEndDate(),
                getCourse()
        );
    }

    @Override
    public String toString() {
        String course =
                "\tName: " + this.course.getName() + "\n" +
                "\tDescription: " + this.course.getDescription() + "\n" +
                "\tCode: " + this.course.getCode();

        try {
            return  "Name: " + getName() + "\n" +
                    "Assigned Course: \n" + course + '\n' +
                    "Batch Creation Date: " + getCreationDate() + "\n" +
                    "Batch Start Date: " + getStartDate() + "\n" +
                    "Batch End Date: " + getEndDate() + "\n" +
                    "Batch Duration (In Days): " + getDescription() + "\n" +
                    "Assigned Faculties:\n\n" + new FacultyList().getTable(new TreeSet<>(assignedFaculties.values()));
        } catch (Exception exception) {
            System.out.println("Problem in creating assigned faculty list\n" + exception);
            return "";
        }
    }

    public Batch(String name, Course course, String description, String startDate, String endDate)
            throws NullArgumentException, EmptyArgumentException, InvalidDateException {
        this.id = UUID.randomUUID();
        this.setName(name);
        this.setCourse(course);
        this.setDescription(description);
        this.setDate(startDate, endDate);
        this.setDurationDays(ChronoUnit.DAYS.between(this.startDate, this.endDate));
        this.batchCreationDate = LocalDateTime.now();
    }

    public Batch(String name, Course course, String description, LocalDateTime startDate, LocalDateTime endDate)
            throws NullArgumentException, EmptyArgumentException, InvalidDateException {
        this.id = UUID.randomUUID();
        this.setName(name);
        this.setCourse(course);
        this.setDescription(description);
        this.setDate(startDate, endDate);
        this.setDurationDays(ChronoUnit.DAYS.between(this.startDate, this.endDate));
        this.batchCreationDate = LocalDateTime.now();
    }
}
