package example.app.ui;

import example.app.entities.compartments.Course;
import example.app.entitylist.CourseList;
import example.app.entitylist.exceptions.DuplicatePresentException;
import example.app.filedatabase.Exceptions.PartitionOverflowException;

import java.util.Scanner;
import java.util.TreeSet;
import java.util.UUID;

/**
 * <h1>Class CourseUI</h1>
 * This class contains methods related to the console UI for courses
 */
public class CourseUI {
    private static String promptName() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("Course Name: ");

            String name = scanner.nextLine();

            if(name.isEmpty())
                System.out.println("\033[31mName cannot be empty\033[0m\n");
            else
                return name;
        }
    }

    private static String promptDescription() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("Course Description: ");

            String description = scanner.nextLine();

            if(description.isEmpty())
                System.out.println("\033[31mDescription cannot be empty\033[0m\n");
            else
                return description;
        }
    }

    private static String promptCode() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("Course Code: ");

            String code = scanner.nextLine();

            if(code.isEmpty())
                System.out.println("\033[31mCode cannot be empty\033[0m\n");
            else
                return code;
        }
    }

    public static Course create() throws Exception {
        System.out.println("\033[1m\033[4mCreate a new course:\033[24m\033[22m\n");
        Course course = null;

        while(true) {
            try {
                course = new Course(
                    promptName(),
                    promptDescription(),
                    promptCode()
                );
                new CourseList().add(course);
                return course;
            }
            catch (PartitionOverflowException exception) {
                System.out.println("Partition in the database is full. Cannot add any more courses.");
                break;
            }
            catch (DuplicatePresentException exception) {
                System.out.println("\033[31mSame course is already present in the database.\033[0m\n");
            }
        }

        return null;
    }

    public static void remove() throws Exception {
        System.out.println("\033[1m\033[4mRemove a course:\033[24m\033[22m\n");

        TreeSet<UUID> ids = new CourseList().getIds();

        System.out.println(new CourseList().getTable(ids));

        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while(ids.size() != 0) {
            System.out.print("Choose a course: ");

            option = Integer.parseInt(scanner.nextLine());

            if(option < 1 || option > ids.size())
                System.out.println("\033[31mInvalid option\033[0m\n");
            else
                break;
        }

        if(ids.size() != 0)
            new CourseList().remove(new CourseList().get(ids.toArray(new UUID[0])[option - 1]));
    }

    public static Course choose() throws Exception {
        System.out.println("\033[1m\033[4mView a course:\033[24m\033[22m\n");
        Course course = null;

        TreeSet<UUID> ids = new CourseList().getIds();

        System.out.println(new CourseList().getTable(ids));

        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while(ids.size() != 0) {
            System.out.print("Choose a course: ");

            option = Integer.parseInt(scanner.nextLine());

            if(option < 1 || option > ids.size())
                System.out.println("\033[31mInvalid option\033[0m\n");
            else
                break;
        }

        if(ids.size() != 0)
            course = new CourseList().get(ids.toArray(new UUID[0])[option - 1]);

        return course;
    }

    public static void view() throws Exception {
        System.out.println("\n" + choose());
    }
}
