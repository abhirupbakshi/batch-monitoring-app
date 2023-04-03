package example.app.ui;

import example.app.entities.compartments.Batch;
import example.app.entities.compartments.Course;
import example.app.entities.compartments.Faculty;
import example.app.entities.exceptions.InvalidDateException;
import example.app.entities.exceptions.NullArgumentException;
import example.app.entitylist.BatchList;
import example.app.entitylist.FacultyList;
import example.app.entitylist.exceptions.DuplicatePresentException;
import example.app.filedatabase.Exceptions.PartitionOverflowException;

import java.util.Scanner;
import java.util.TreeSet;
import java.util.UUID;

/**
 * <h1>Class BatchUI</h1>
 * This class contains methods related to the console UI for batches
 */
public class BatchUI {
    private static String promptName() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("Batch Name: ");

            String name = scanner.nextLine();

            if(name.isEmpty())
                System.out.println("\033[31mBatch name cannot be empty\033[0m\n");
            else
                return name;
        }
    }

    private static String promptDescription() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("Batch Description: ");

            String description = scanner.nextLine();

            if(description.isEmpty())
                System.out.println("\033[31mBatch Description cannot be empty\033[0m\n");
            else
                return description;
        }
    }

    private static String promptDate(String dateType) {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("Batch " + dateType + " (yyyy-MM-dd): ");

            String date = scanner.nextLine();

            if(date.isEmpty())
                System.out.println("\033[31m" + "Batch " + dateType + " cannot be empty\033[0m\n");
            else
                return date;
        }
    }

    private static Course promptCourse() throws Exception {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("\033[1m1\033[22m: Add a new course");
            System.out.println("\033[1m2\033[22m: Choose from existing courses");

            System.out.print("Enter Choice: ");

            String choice = scanner.nextLine();

            if(choice.isEmpty())
                System.out.println("\033[31mInvalid input\033[0m\n");
            else if(Integer.parseInt(choice) == 1)
                return CourseUI.create();
            else if(Integer.parseInt(choice) == 2) {
                return CourseUI.choose();
            }
            else
                System.out.println("\033[31mInvalid input\033[0m\n");
        }
    }

    public static Batch create() throws Exception {
        System.out.println("\033[1m\033[4mCreate a new batch:\033[24m\033[22m\n");
        Batch batch = null;

        while(true) {
            try {
                batch = new Batch(
                        promptName(),
                        promptCourse(),
                        promptDescription(),
                        promptDate("Start Date") + "T00:00:00.000",
                        promptDate("End Date") + "T00:00:00.000"
                );

                new BatchList().add(batch);

                break;
            }
            catch (PartitionOverflowException exception) {
                System.out.println("Partition in the database is full. Cannot add any more batches.");
                break;
            }
            catch (DuplicatePresentException exception) {
                System.out.println("\033[31mSame batch is already present in the database.\033[0m\n");
            }
            catch(NullArgumentException exception) {
                System.out.println("\033[31m" + exception.getMessage() + "\033[0m\n");
            }
            catch(InvalidDateException exception) {
                System.out.println("\033[31m" + exception.getMessage() + "\033[0m\n");
                System.out.println("""
                        Valid dates constrains are:
                        1. Has to be in the format yyyy-MM-dd
                        2. Start date must be before end date always
                        
                        """);
            }
        }

        return batch;
    }

    public static void remove() throws Exception {
        System.out.println("\033[1m\033[4mRemove a batch:\033[24m\033[22m\n");

        TreeSet<UUID> ids = new BatchList().getIds();

        System.out.println(new BatchList().getTable(ids));

        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while(ids.size() != 0) {
            System.out.print("Choose a batch: ");

            option = Integer.parseInt(scanner.nextLine());

            if(option < 1 || option > ids.size())
                System.out.println("\033[31mInvalid option\033[0m\n");
            else
                break;
        }

        if(ids.size() != 0)
            new BatchList().remove(new BatchList().get(ids.toArray(new UUID[0])[option - 1]));
    }

    public static Batch choose() throws Exception {
        Batch batch = null;

        TreeSet<UUID> ids = new BatchList().getIds();

        System.out.println(new BatchList().getTable(ids));

        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while(ids.size() != 0) {
            System.out.print("Choose a batch: ");

            option = Integer.parseInt(scanner.nextLine());

            if(option < 1 || option > ids.size())
                System.out.println("\033[31mInvalid option\033[0m\n");
            else
                break;
        }

        if(ids.size() != 0)
            batch = new BatchList().get(ids.toArray(new UUID[0])[option - 1]);

        return batch;
    }

    public static void view() throws Exception {
        System.out.println("\033[1m\033[4mView a batch:\033[24m\033[22m\n");
        System.out.println("\n" + choose());
    }

    public static void addFaculty() throws Exception {
        System.out.println("\033[1m\033[4mAdd a faculty to a batch:\033[24m\033[22m\n");

        Batch batchToEdit = choose();

        System.out.println("\nFollowing are the assigned faculties:\n\n" + new FacultyList().getTable(
                new TreeSet<>(batchToEdit.getAssignedFaculties().values())
        ));

        Scanner scanner = new Scanner(System.in);
        int option = 0;
        Faculty facultyToBe_Added = null;

        while(true) {
            System.out.println("\033[1m1\033[22m: Create and add a new faculty");
            System.out.println("\033[1m2\033[22m: Add from the existing list of faculties in database");

            System.out.print("Enter Choice: ");

            option = Integer.parseInt(scanner.nextLine());

            if(option == 1) {
                facultyToBe_Added = FacultyUI.create();
                break;
            }
            else if(option == 2) {
                facultyToBe_Added = FacultyUI.choose();
                break;
            }
            else
                System.out.println("\033[31mInvalid option\033[0m\n");
        }

        if(facultyToBe_Added != null)
            batchToEdit.addFaculties(facultyToBe_Added);

        new BatchList().edit(batchToEdit);
    }

    public static void removeFaculty() throws Exception {
        System.out.println("\033[1m\033[4mRemove a faculty from a batch:\033[24m\033[22m\n");

        Batch batchToEdit = choose();
        TreeSet<UUID> ids = new TreeSet<>(batchToEdit.getAssignedFaculties().values());

        System.out.println("\nFollowing are the assigned faculties:\n\n" + new FacultyList().getTable(ids));

        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while(true) {
            System.out.print("Choose a faculty: ");

            if(batchToEdit.getAssignedFaculties().size() == 0) {
                System.out.println("\033[31mNo faculties are assigned to this batch\033[0m\n");
                break;
            }

            option = Integer.parseInt(scanner.nextLine());

            if(option < 1 || option > batchToEdit.getAssignedFaculties().size()) {
                System.out.println("\033[31mInvalid option\033[0m\n");
            }
            else
                break;
        }

        if(ids.size() != 0)
            batchToEdit.removeFaculties(
                    new FacultyList().get(ids.toArray(new UUID[0])[option - 1])
            );

        new BatchList().edit(batchToEdit);
    }
}
