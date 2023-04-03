package example.app.ui;

import example.app.entities.users.FacultyUser;
import example.app.entitylist.UserList;
import example.app.entitylist.exceptions.DuplicatePresentException;
import example.app.filedatabase.Exceptions.PartitionOverflowException;

import java.util.Scanner;
import java.util.TreeSet;
import java.util.UUID;

/**
 * <h1>Class FacultyUserUI</h1>
 * This class contains methods related to console UI for faculty users
 */
public class FacultyUserUI {
    private static String promptUserName() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("Create Username: ");

            String uname = scanner.nextLine();

            if(uname.isEmpty())
                System.out.println("\033[31mUsername cannot be empty\033[0m\n");
            else
                return uname;
        }
    }

    private static String promptPassword() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("Create Password: \033[8m");

            String password = scanner.nextLine();
            System.out.print("\033[28m");

            if(password.isEmpty())
                System.out.println("\033[31mPassword cannot be empty\033[0m\n");
            else
                return password;
        }
    }

    private static String promptFirstName() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("First Name: ");

            String firstName = scanner.nextLine();

            if(firstName.isEmpty())
                System.out.println("\033[31mFirst Name cannot be empty\033[0m\n");
            else
                return firstName;
        }
    }

    private static String promptLastName() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("Last Name: ");

            String lastName = scanner.nextLine();

            if(lastName.isEmpty())
                System.out.println("\033[31mLast Name cannot be empty\033[0m\n");
            else
                return lastName;
        }
    }

    private static String promptEmail() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("Email: ");

            String email = scanner.nextLine();

            if(email.isEmpty())
                System.out.println("\033[31mEmail cannot be empty\033[0m\n");
            else
                return email;
        }
    }

    public static FacultyUser create() throws Exception {
        System.out.println("\033[1m\033[4mCreate a new Faculty User Account:\033[24m\033[22m\n");
        FacultyUser facultyUser = null;

        while(true) {
            try {
                facultyUser = new FacultyUser(
                    promptUserName(),
                    promptPassword(),
                    promptFirstName(),
                    promptLastName(),
                    promptEmail()
                );

                new UserList().add(facultyUser);

                break;
            }
            catch (PartitionOverflowException exception) {
                System.out.println("Partition in the database is full. Cannot add any more Users.");
                break;
            }
            catch (DuplicatePresentException exception) {
                System.out.println("\033[31mSame user account is already present in the database.\033[0m\n");
            }
        }

        return facultyUser;
    }

    public static void remove() throws Exception {
        System.out.println("\033[1m\033[4mRemove a FacultyUser:\033[24m\033[22m\n");

        TreeSet<UUID> ids = new UserList().getIds();

        System.out.println(new UserList().getTable(ids));

        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while(ids.size() != 0) {
            System.out.print("Choose a Faculty User: ");

            option = Integer.parseInt(scanner.nextLine());

            if(option < 1 || option > ids.size())
                System.out.println("\033[31mInvalid option\033[0m\n");
            else
                break;
        }

        if(ids.size() != 0)
            new UserList().remove(new UserList().get(ids.toArray(new UUID[0])[option - 1]));
    }

    public static FacultyUser choose() throws Exception {
        System.out.println("\033[1m\033[4mView a FacultyUser:\033[24m\033[22m\n");

        TreeSet<UUID> ids = new UserList().getIds();

        System.out.println(new UserList().getTable(ids));

        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while(ids.size() != 0) {
            System.out.print("Choose a Faculty User: ");

            option = Integer.parseInt(scanner.nextLine());

            if(option < 1 || option > ids.size())
                System.out.println("\033[31mInvalid option\033[0m\n");
            else
                break;
        }

        if(ids.size() != 0)
            return (FacultyUser) new UserList().get(ids.toArray(new UUID[0])[option - 1]);

        return null;
    }

    public static void view() throws Exception {
        System.out.println(choose());
    }
}
