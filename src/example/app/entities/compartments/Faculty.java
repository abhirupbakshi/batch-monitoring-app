package example.app.entities.compartments;

import example.app.entities.users.FacultyUser;
import example.app.exceptions.EmptyArgumentException;
import example.app.exceptions.NullArgumentException;
import example.app.services.Batches;
import example.app.services.Faculties;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.UUID;

public class Faculty implements Serializable {
    private final UUID id;
    private String name;
    private String description;
    private String code;
    private FacultyUser facultyUser;
    private final LocalDate facultyCreationDate;
    private final HashMap<UUID, Batch> assignedBatches = new HashMap<>();

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name)
            throws NullArgumentException, EmptyArgumentException {
        if(name == null)
            throw new NullArgumentException("Cannot set the value of \"name\" in class \"Faculty\" to null");
        if(name.isEmpty())
            throw new EmptyArgumentException("Cannot set the value of \"name\" in class \"Faculty\" to an empty string");

        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description)
            throws NullArgumentException {
        if(description == null)
            throw new NullArgumentException("Cannot set the value of \"description\" in class \"Faculty\" to null");

        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code)
            throws NullArgumentException, EmptyArgumentException {
        if(code == null)
            throw new NullArgumentException("Cannot set the value of \"code\" in class \"Faculty\" to null");
        if(code.isEmpty())
            throw new EmptyArgumentException("Cannot set the value of \"code\" in class \"Faculty\" to an empty string");

        this.code = code;
    }

    public FacultyUser getFacultyUser() {
        return facultyUser;
    }

    public void setFacultyUser(FacultyUser facultyUser) throws NullArgumentException {
        if(facultyUser == null)
            throw new NullArgumentException("Cannot set the value of \"facultyUser\" in class \"Faculty\" to null");

        this.facultyUser = facultyUser;
    }

    public LocalDate getFacultyCreationDate() {
        return facultyCreationDate;
    }

    public HashMap<UUID, Batch> getAssignedBatches() {
        return assignedBatches;
    }

    public void addBatches(Batch... batches) throws NullArgumentException {
        for(Batch batch : batches) {
            if(batch == null)
                throw new NullArgumentException("Cannot add null to \"assignedBatches\" in class \"Faculty\"");
        }

        for(Batch batch : batches) {
            assignedBatches.put(batch.getId(), batch);
        }
    }

    public void removeBatches(Batch... batches) {
        for(Batch batch : batches) {
            assignedBatches.remove(batch.getId());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faculty faculty)) return false;
        return Objects.equals(getName(), faculty.getName()) && Objects.equals(getDescription(), faculty.getDescription()) && Objects.equals(getCode(), faculty.getCode());
    }

    @Override
    public String toString() {
        String facultyUser =
                "\tName: " + this.facultyUser.getFirstName() + " " + this.facultyUser.getLastName() + "\n" +
                "\tEmail: " + this.facultyUser.getEmail();

        return  "Name: " + this.name + "\n" +
                "Description: " + this.description + "\n" +
                "Code: " + this.code + "\n" +
                "Faculty Creation Date: " + facultyCreationDate + "\n" +
                "Faculty User: \n" + facultyUser + "\n" +
                "Assigned Batches:\n\n" + new Batches().createTabularFormat(new LinkedList<>(assignedBatches.values()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getCode());
    }

    public Faculty(String name, String description, String code, FacultyUser facultyUser)
            throws NullArgumentException, EmptyArgumentException {
        this.setName(name);
        this.setDescription(description);
        this.setCode(code);
        this.setFacultyUser(facultyUser);
        this.facultyCreationDate = LocalDate.now();
        this.id = UUID.randomUUID();
    }
}
