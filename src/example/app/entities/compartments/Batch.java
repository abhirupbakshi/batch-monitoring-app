package example.app.entities.compartments;

import example.app.exceptions.EmptyArgumentException;
import example.app.exceptions.InvalidDateException;
import example.app.exceptions.NullArgumentException;
import example.app.utility.CheckDate;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.UUID;

public class Batch {
    private final UUID id;
    private String name;
    private Course course;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private long durationDays;
    private final LocalDate batchCreationDate;
    private final HashMap<UUID, Faculty> assignedFaculties = new HashMap<>();

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name)
            throws NullArgumentException, EmptyArgumentException {
        if(name == null)
            throw new NullArgumentException("Cannot set the value of \"name\" in class \"Batch\" to null");
        if(name.isEmpty())
            throw new EmptyArgumentException("Cannot set the value of \"name\" in class \"Batch\" to an empty string");

        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) throws NullArgumentException {
        if(course == null)
            throw new NullArgumentException("Cannot set the value of \"course\" in class \"Batch\" to null");

        this.course = course;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description)
            throws NullArgumentException {
        if(description == null)
            throw new NullArgumentException("Cannot set the value of \"description\" in class \"Batch\" to null");

        this.description = description;
    }

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

    public void setDate(LocalDate startDate, long durationDays)
            throws NullArgumentException, InvalidDateException {
        if(startDate == null)
            throw new NullArgumentException("Cannot set the value of \"startDate\" in class \"Batch\" to null");
        if(durationDays <= 0)
            throw new InvalidDateException("Cannot set the value of \"durationDays\" in class \"Batch\" to a negative or zero value");

        setDate(startDate, startDate.plusDays(durationDays));
    }

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate)
            throws InvalidDateException, NullArgumentException {
        if(startDate == null)
            throw new NullArgumentException("Cannot set the value of \"startDate\" in class \"Batch\" to null");

        setDate(startDate, this.endDate);
    }

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

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate)
            throws InvalidDateException, NullArgumentException {
        if(endDate == null)
            throw new NullArgumentException("Cannot set the value of \"endDate\" in class \"Batch\" to null");

        setDate(this.startDate, endDate);
    }

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

    public long getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(long durationDays)
            throws InvalidDateException, NullArgumentException {
        setDate(this.startDate, durationDays);
    }

    public HashMap<UUID, Faculty> getAssignedFaculties() {
        return assignedFaculties;
    }

    public void addFaculties(Faculty... faculties) throws NullArgumentException {
        for(Faculty faculty : faculties) {
            if(faculty == null)
                throw new NullArgumentException("Cannot add null to \"assignedFaculties\" in class \"Batch\"");
        }

        for(Faculty faculty : faculties) {
            assignedFaculties.put(faculty.getId(), faculty);
        }
    }

    public void removeFaculties(Faculty... faculties) {
        for(Faculty faculty : faculties) {
            assignedFaculties.remove(faculty.getId());
        }
    }

    public LocalDate getBatchCreationDate() {
        return batchCreationDate;
    }

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
