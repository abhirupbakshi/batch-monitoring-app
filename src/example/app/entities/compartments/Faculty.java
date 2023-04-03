package example.app.entities.compartments;

import example.app.entities.users.FacultyUser;
import example.app.entities.exceptions.EmptyArgumentException;
import example.app.entities.exceptions.NullArgumentException;
import example.app.entities.FileDatabaseCompatible;
import example.app.entitylist.BatchList;
import example.app.entitylist.exceptions.DuplicatePresentException;
import example.app.filedatabase.Exceptions.CannotCreateFileException;
import example.app.filedatabase.Exceptions.FileDatabaseConfigFileException;
import example.app.filedatabase.Exceptions.FileDatabaseInternalException;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <h1>Class Faculty</h1>
 * This class represents a faculty and its attributes.
 * <ul>
 *     <li>Id: UUID v4 that is immutable after assignment</li>
 *     <li>Code: code of the faculty</li>
 *     <li>Description: description of the faculty (optional)</li>
 *     <li>Faculty User: Faculty user who is assigned to this faculty</li>
 *     <li>Faculty Creation Date: Date and time on which the faculty was created. It's immutable</li>
 *     <li>Assigned Batches: A list of batches that has been assigned to the faculty</li>
 *     <li>For two faculty to be same, only code needs to be equal</li>
 * </ul>
 */
public class Faculty implements Serializable, FileDatabaseCompatible {
    private final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private final UUID id;
    private String code;
    private String description;
    private FacultyUser facultyUser;
    private final LocalDateTime facultyCreationDate;
    private final TreeMap<LocalDateTime, UUID> assignedBatches = new TreeMap<>();

    /**
     * Getter for faculty id
     * @return UUID v4
     */
    public UUID getId() {
        return id;
    }

    /**
     * Getter for faculty code
     * @return code of the faculty
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter of the faculty code
     * @param code code of the faculty
     * @throws NullArgumentException if code is null
     * @throws EmptyArgumentException if code is empty
     */
    public void setCode(String code)
            throws NullArgumentException, EmptyArgumentException {
        if(code == null)
            throw new NullArgumentException("Cannot set the value of \"code\" in class \"Faculty\" to null");
        if(code.isEmpty())
            throw new EmptyArgumentException("Cannot set the value of \"code\" in class \"Faculty\" to an empty string");

        this.code = code;
    }

    /**
     * Getter for faculty description
     * @return description of the faculty
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter of the faculty description. It can be an empty string as it's optional
     * @param description description of the faculty
     * @throws NullArgumentException if description is null
     */
    public void setDescription(String description)
            throws NullArgumentException {
        if(description == null)
            throw new NullArgumentException("Cannot set the value of \"description\" in class \"Faculty\" to null");

        this.description = description;
    }

    /**
     * Getter for faculty user
     * @return Faculty user
     */
    public FacultyUser getFacultyUser() {
        return facultyUser;
    }

    /**
     * Setter of the faculty user
     * @param facultyUser Faculty user to be set
     * @throws NullArgumentException if facultyUser is null
     */
    public void setFacultyUser(FacultyUser facultyUser) throws NullArgumentException {
        if(facultyUser == null)
            throw new NullArgumentException("Cannot set the value of \"facultyUser\" in class \"Faculty\" to null");

        this.facultyUser = facultyUser;
    }

    /**
     * Getter for faculty creation date
     * @return date and time on which the faculty was created
     */
    public LocalDateTime getCreationDate() {
        return LocalDateTime.parse(facultyCreationDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
    }

    /**
     * Getter for assigned batches
     * @return TreeMap of assigned batches
     */
    public TreeMap<LocalDateTime, UUID> getAssignedBatches() {
        return assignedBatches;
    }

    /**
     * /**
     * This method adds a list of batches to the faculty
     * @param batches list of batches
     * @throws NullArgumentException if any of the batch is null. In that case it does not add the batches
     * @throws DuplicatePresentException
     * @throws CannotCreateFileException
     * @throws FileDatabaseConfigFileException
     * @throws FileDatabaseInternalException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void addBatches(Batch... batches)
            throws NullArgumentException, DuplicatePresentException, CannotCreateFileException,
            FileDatabaseConfigFileException, FileDatabaseInternalException,
            IOException, ClassNotFoundException {
        for(Batch batch : batches) {
            if(batch == null)
                throw new NullArgumentException("Cannot add null to \"assignedBatches\" in class \"Faculty\"");

            for(LocalDateTime key : assignedBatches.keySet()) {
                if(new BatchList().get(assignedBatches.get(key)).equals(batch))
                    throw new DuplicatePresentException("Batch with id " + batch.getId() + " is already assigned to the faculty");
            }
        }

        for(Batch batch : batches) {
            assignedBatches.put(batch.getCreationDate(), batch.getId());
        }
    }

    /**
     * This method removes a list of batches from the faculty
     * @param batches list of batches to be removed
     */
    public void removeBatches(Batch... batches) {
        for(Batch batch : batches) {
            assignedBatches.remove(batch.getCreationDate());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faculty faculty)) return false;
        return Objects.equals(getCode(), faculty.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }

    @Override
    public String toString() {
        String facultyUser =
                "\tName: " + this.facultyUser.getFirstName() + " " + this.facultyUser.getLastName() + "\n" +
                "\tEmail: " + this.facultyUser.getEmail();

        try {
            return  "Code: " + getCode() + "\n" +
                    "Description: " + getDescription() + "\n" +
                    "Faculty Creation Date: " + getCreationDate() + "\n" +
                    "Faculty User: \n" + facultyUser + "\n" +
                    "Assigned Batches:\n\n" + new BatchList().getTable(new TreeSet<>(assignedBatches.values()));
        }
        catch (Exception exception) {
            System.err.println("Problem in creating assigned batch list\n" + exception);
            return "";
        }
    }

    public Faculty(String code, String description, FacultyUser facultyUser)
            throws NullArgumentException, EmptyArgumentException {
        this.setCode(code);
        this.setDescription(description);
        this.setFacultyUser(facultyUser);
        this.facultyCreationDate = LocalDateTime.now();
        this.id = UUID.randomUUID();
    }
}
