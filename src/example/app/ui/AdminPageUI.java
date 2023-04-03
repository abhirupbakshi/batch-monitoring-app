package example.app.ui;

import java.util.Scanner;

/**
 * <h1>Class AdminPageUI</h1>
 * This class contains methods related to the console UI for admin pages
 */
public class AdminPageUI {
    public static void login() throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\033[32mLogin Successful, Welcome Admin\033[0m\n");

        while(true) {
            System.out.println("\033[1m1. Create Faculty\033[0m");
            System.out.println("\033[1m2. Create Batch\033[0m");
            System.out.println("\033[1m3. Create Course\033[0m");
            System.out.println("\033[1m4. Create Faculty User\033[0m");

            System.out.println("\033[1m5. View Faculty\033[0m");
            System.out.println("\033[1m6. View Batch\033[0m");
            System.out.println("\033[1m7. View Course\033[0m");
            System.out.println("\033[1m8. View Faculty User\033[0m");

            System.out.println("\033[1m9. remove Faculty\033[0m");
            System.out.println("\033[1m10. remove Batch\033[0m");
            System.out.println("\033[1m11. remove Course\033[0m");
            System.out.println("\033[1m12. remove Faculty User\033[0m");

            System.out.println("\033[1m13. Add batch to Faculty\033[0m");
            System.out.println("\033[1m14. Add Faculty to Batch\033[0m");

            System.out.println("\033[1m15. Log Out\033[0m");
            System.out.println("\033[1m16. Exit\033[0m");

            System.out.print("\nEnter Choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch(choice) {
                case 1:
                    FacultyUI.create();
                    System.out.println("\033[32mDone\033[0m\n");
                    break;
                case 2:
                    BatchUI.create();
                    System.out.println("\033[32mDone\033[0m\n");
                    break;
                case 3:
                    CourseUI.create();
                    System.out.println("\033[32mDone\033[0m\n");
                    break;
                case 4:
                    FacultyUserUI.create();
                    System.out.println("\033[32mDone\033[0m\n");
                    break;


                case 5:
                    FacultyUI.view();
                    System.out.println("\033[32mDone\033[0m\n");
                    break;
                case 6:
                    BatchUI.view();
                    System.out.println("\033[32mDone\033[0m\n");
                    break;
                case 7:
                    CourseUI.view();
                    System.out.println("\033[32mDone\033[0m\n");
                    break;
                case 8:
                    FacultyUserUI.view();
                    System.out.println("\033[32mDone\033[0m\n");
                    break;


                case 9:
                    FacultyUI.remove();
                    System.out.println("\033[32mDone\033[0m\n");
                    break;
                case 10:
                    BatchUI.remove();
                    System.out.println("\033[32mDone\033[0m\n");
                    break;
                case 11:
                    CourseUI.remove();
                    System.out.println("\033[32mDone\033[0m\n");
                    break;
                case 12:
                    FacultyUserUI.remove();
                    System.out.println("\033[32mDone\033[0m\n");
                    break;


                case 13:
                    FacultyUI.addBatch();
                    System.out.println("\033[32mDone\033[0m\n");
                    break;
                case 14:
                    BatchUI.addFaculty();
                    System.out.println("\033[32mDone\033[0m\n");
                    break;


                case 15:
                    return;
                case 16:
                    System.exit(0);
                default:
                    System.out.println("\033[31mInvalid choice\033[0m\n");
            }
        }
    }
}
