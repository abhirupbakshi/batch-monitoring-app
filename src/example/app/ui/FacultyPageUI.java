package example.app.ui;

import example.app.entities.users.FacultyUser;
import example.app.entitylist.FacultyList;
import example.app.entitylist.UserList;
import example.app.filedatabase.Exceptions.CannotCreateFileException;
import example.app.filedatabase.Exceptions.FileDatabaseConfigFileException;
import example.app.filedatabase.Exceptions.FileDatabaseInternalException;

import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.UUID;

/**
 * <h1>Class FacultyPageUI</h1>
 * This class contains methods related to the console UI for faculty pages
 */
public class FacultyPageUI {
    public static TreeSet<UUID> findFaculties(FacultyUser facultyUser)
            throws CannotCreateFileException, FileDatabaseConfigFileException,
            IOException, ClassNotFoundException, FileDatabaseInternalException {
        TreeSet<UUID> faculties = new TreeSet<>();

        for(UUID key : new FacultyList().getIds()) {
           if(new FacultyList().get(key).getFacultyUser().equals(facultyUser)) {
               faculties.add(key);
           }
        }

        return faculties;
    }

    public static void login(FacultyUser facultyUser)
            throws Exception {
        System.out.println("Done faculty");

        Scanner scanner = new Scanner(System.in);

        while(true) {
            TreeSet<UUID> facultyList = findFaculties(facultyUser);

            if(facultyList.isEmpty()) {
                System.out.println("\033[31mNo faculty was assigned to you.\033[0m\n");
            }
            else {
                System.out.println("\033[1m\033[4mFaculty List:\033[24m\033[22m");
                System.out.println(new FacultyList().getTable(facultyList));
            }

            System.out.print("\033[1m1:\033[0m Create a new Faculty\n");
            System.out.print("\033[1m2:\033[0m Delete Account\n");
            System.out.print("\033[1m3:\033[0m Log out\n");
            System.out.print("\033[1m4:\033[0m Exit\n");

            System.out.print("\nEnter Choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch(choice) {
                case 1:
                    FacultyUI.create();
                    System.out.println("\033[32mDone\033[0m\n");
                    break;
                case 2:
                    new UserList().remove(facultyUser);
                    return;
                case 3:
                    return;
                case 4:
                    System.exit(0);
            }
        }
    }
}
