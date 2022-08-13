package UI;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuManager {

    public static String[] GetMenu() {
        return MM;
    }

    public static void PrintMenu(String[] Menu) {
        for (String menu : Menu) {
            System.out.println(menu);
        }
    }

    public static int getInputForMenu(String[] menu) {
        Scanner sc = new Scanner(System.in);
        int length = 9;
        int userIntegerInput = 0;
        boolean valid = false;

        PrintMenu(menu);
        do {
            System.out.println("Please choose an option (1-8)");
            try {
                userIntegerInput = sc.nextInt();
                if (userIntegerInput < 9 && userIntegerInput > 0)
                    valid = true;
            } catch (InputMismatchException e) {
                sc.nextLine();
            }

        } while (!valid);
        return userIntegerInput;
    }

    public static boolean isValidInRange(int input, int a, int b) {
        return input >= a && input <= b;
    }

    private static final String[] MM = {
            " ", "------ Main Menu: ------",
            "1- Load an XML file.",
            "2- Display machine details.",
            "3- Configure machine manually.",
            "4- Configure machine automatically.",
            "5- Process new input",
            "6- Reset Configurations.",
            "7- History and Statistics",
            "8- Quit."
    };
}