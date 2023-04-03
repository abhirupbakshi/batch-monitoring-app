package example.app;

import example.app.entities.users.Admin;
import example.app.entities.users.FacultyUser;
import example.app.entitylist.UserList;
import example.app.filedatabase.Exceptions.CannotCreateFileException;
import example.app.filedatabase.Exceptions.FileDatabaseConfigFileException;
import example.app.filedatabase.Exceptions.FileDatabaseInternalException;
import example.app.filedatabase.FileDatabaseConfigFile;
import example.app.ui.AdminPageUI;
import example.app.ui.FacultyPageUI;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static FacultyUser findUser(String username)
            throws CannotCreateFileException, FileDatabaseConfigFileException,
            IOException, ClassNotFoundException, FileDatabaseInternalException {
        for(UUID key : new UserList().getIds()) {
            FacultyUser facultyUser = (FacultyUser) new UserList().get(key);

            if(username.equals(facultyUser.getUsername()))
                return facultyUser;
        }

        return null;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Press \033[1m1\033[22m: Admin Login");
            System.out.println("Press \033[1m2\033[22m: Faculty User Login");
            System.out.println("Press \033[1m3\033[22m: Create a new database config file");
            System.out.println("Press \033[1m4\033[22m: Exit");

            System.out.print("\nEnter Choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch(choice) {
                case 1:
                    while(true) {
                        System.out.println("\033[1m\033[4mAdmin Login:\033[24m\033[22m");
                        System.out.print("Username: ");
                        String username = scanner.nextLine();
                        System.out.print("Password: ");
                        String password = scanner.nextLine();

                        if(username.isEmpty() || password.isEmpty())
                            System.out.println("\033[31mUsername and password cannot be empty\033[0m\n");
                        else {
                            if(Admin.credentialsEquals(username, password)) {
                                AdminPageUI.login();
                                break;
                            }
                            else
                                System.out.println("\033[31mInvalid Credentials\033[0m\n");
                        }
                    }
                    break;
                case 2:
                    while(true) {
                        System.out.println("\033[1m\033[4mFaculty User Login:\033[24m\033[22m");
                        System.out.print("Username: ");
                        String username = scanner.nextLine();
                        System.out.print("Password: ");
                        String password = scanner.nextLine();

                        if(username.isEmpty() || password.isEmpty())
                            System.out.println("\033[31mUsername and password cannot be empty\033[0m\n");
                        else {
                            FacultyUser user = findUser(username);
                            if(user == null) {
                                System.out.println("\033[31mUser not found\033[0m\n");
                                break;
                            }
                            else if(user.credentialsEquals(username, password)) {
                                FacultyPageUI.login(user);
                                break;
                            }
                            else
                                System.out.println("\033[31mInvalid Credentials\033[0m\n");
                        }
                    }
                    break;
                case 3:
                    FileDatabaseConfigFile.create();
                    break;
                case 4:
                    System.out.println("\033[1m\033[4mExiting...\033[24m\033[22m\n");
                    return;
                default:
                    System.out.println("\033[31mInvalid choice\033[0m\n");
            }
        }
    }
}
