package example.app.ui;

import example.app.entities.compartments.Batch;
import example.app.entities.compartments.Faculty;
import example.app.entities.exceptions.NullArgumentException;
import example.app.entities.users.FacultyUser;
import example.app.entitylist.BatchList;
import example.app.entitylist.FacultyList;
import example.app.entitylist.exceptions.DuplicatePresentException;
import example.app.filedatabase.Exceptions.PartitionOverflowException;

import java.util.Scanner;
import java.util.TreeSet;
import java.util.UUID;

/**
 * <h1>Class FacultyUI</h1>
 * This class contains methods related to the console UI for faculties
 */
public class FacultyUI {
    private static String promptCode() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("Faculty Code: ");

            String code = scanner.nextLine();

            if(code.isEmpty())
                System.out.println("\033[31mFaculty code cannot be empty\033[0m\n");
            else
                return code;
        }
    }

    private static String promptDescription() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("Faculty Description: ");

            String description = scanner.nextLine();

            if(description.isEmpty())
                System.out.println("\033[31mFaculty Description cannot be empty\033[0m\n");
            else
                return description;
        }
    }

    private static FacultyUser promptFacultyUser() throws Exception {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("\033[1m1\033[22m: Add a new faculty user");
            System.out.println("\033[1m2\033[22m: Choose from existing faculty users");

            System.out.print("Enter Choice: ");

            String choice = scanner.nextLine();

            if(choice.isEmpty())
                System.out.println("\033[31mInvalid input\033[0m\n");
            else if(Integer.parseInt(choice) == 1)
                return FacultyUserUI.create();
            else if(Integer.parseInt(choice) == 2) {
                return FacultyUserUI.choose();
            }
            else
                System.out.println("\033[31mInvalid input\033[0m\n");
        }
    }

    public static Faculty create() throws Exception {
        System.out.println("\033[1m\033[4mCreate a new faculty:\033[24m\033[22m\n");
        Faculty faculty = null;

        while(true) {
            try {
                faculty = new Faculty(
                        promptCode(),
                        promptDescription(),
                        promptFacultyUser()
                );

                new FacultyList().add(faculty);

                break;
            }
            catch (PartitionOverflowException exception) {
                System.out.println("Partition in the database is full. Cannot add any more faculties.");
                break;
            }
            catch (DuplicatePresentException exception) {
                System.out.println("\033[31mSame faculty is already present in the database.\033[0m\n");
            }
            catch(NullArgumentException exception) {
                System.out.println("\033[31m" + exception.getMessage() + "\033[0m\n");
            }
        }

        return faculty;
    }

    public static void remove() throws Exception {
        System.out.println("\033[1m\033[4mRemove a faculty:\033[24m\033[22m\n");

        TreeSet<UUID> ids = new FacultyList().getIds();

        System.out.println(new FacultyList().getTable(ids));

        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while(ids.size() != 0) {
            System.out.print("Choose a faculty: ");

            option = Integer.parseInt(scanner.nextLine());

            if(option < 1 || option > ids.size())
                System.out.println("\033[31mInvalid option\033[0m\n");
            else
                break;
        }

        if(ids.size() != 0)
            new FacultyList().remove(new FacultyList().get(ids.toArray(new UUID[0])[option - 1]));
    }

    public static Faculty choose() throws Exception {
        System.out.println("\033[1m\033[4mView a faculty:\033[24m\033[22m\n");
        Faculty faculty = null;

        TreeSet<UUID> ids = new FacultyList().getIds();

        System.out.println(new FacultyList().getTable(ids));

        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while(ids.size() != 0) {
            System.out.print("Choose a faculty: ");

            option = Integer.parseInt(scanner.nextLine());

            if(option < 1 || option > ids.size())
                System.out.println("\033[31mInvalid option\033[0m\n");
            else
                break;
        }

        if(ids.size() != 0)
            faculty = new FacultyList().get(ids.toArray(new UUID[0])[option - 1]);

        return faculty;
    }

    public static void view() throws Exception {
        System.out.println("\n" + choose());
    }

    public static void addBatch() throws Exception {
        System.out.println("\033[1m\033[4mAdd a batch to a faculty:\033[24m\033[22m\n");

        Faculty facultyToEdit = choose();

        System.out.println("\nFollowing are the assigned batches:\n\n" + new BatchList().getTable(
                new TreeSet<>(facultyToEdit.getAssignedBatches().values())
        ));

        Scanner scanner = new Scanner(System.in);
        int option = 0;
        Batch batchToBe_Added = null;

        while(true) {
            System.out.println("\033[1m1\033[22m: Create and add a new batch");
            System.out.println("\033[1m2\033[22m: Add from the existing list of batches in database");

            System.out.print("Enter Choice: ");

            option = Integer.parseInt(scanner.nextLine());

            if(option == 1) {
                batchToBe_Added = BatchUI.create();
                break;
            }
            else if(option == 2) {
                batchToBe_Added = BatchUI.choose();
                break;
            }
            else
                System.out.println("\033[31mInvalid option\033[0m\n");
        }

        if(batchToBe_Added != null)
            facultyToEdit.addBatches(batchToBe_Added);

        new FacultyList().edit(facultyToEdit);
    }

    public static void removeBatch() throws Exception {
        System.out.println("\033[1m\033[4mRemove a batch from a faculty:\033[24m\033[22m\n");

        Faculty facultyToEdit = choose();
        TreeSet<UUID> ids = new TreeSet<>(facultyToEdit.getAssignedBatches().values());

        System.out.println("\nFollowing are the assigned batches:\n\n" + new BatchList().getTable(ids));

        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while(true) {
            System.out.print("Choose a batch: ");

            if(facultyToEdit.getAssignedBatches().size() == 0) {
                System.out.println("\033[31mNo batches are assigned to this faculty\033[0m\n");
                break;
            }

            option = Integer.parseInt(scanner.nextLine());

            if(option < 1 || option > facultyToEdit.getAssignedBatches().size()) {
                System.out.println("\033[31mInvalid option\033[0m\n");
            }
            else
                break;
        }

        if(ids.size() != 0)
            facultyToEdit.removeBatches(
                    new BatchList().get(ids.toArray(new UUID[0])[option - 1])
            );

        new FacultyList().edit(facultyToEdit);
    }
}
