package cmpt213.assignment1.packagedeliveriestracker;


import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * A class to add a comparator for custom sorting by date
 *
 * @author Omansh
 */
class SortByDate implements Comparator<PkgInfo> {

    public int compare(PkgInfo a, PkgInfo b) {
        return a.getDeliveryDate().compareTo(b.getDeliveryDate());
    }
}

/**
 * Input/output class for displaying menu and handling user input
 *
 * @author Omansh
 */
public class MainMenu {

    private final ArrayList<String> choices = new ArrayList<>(Arrays.asList("List all packages", "Add a package", "Remove a package", "List overdue packages", "List upcoming packages", "Mark package as delivered", "Exit"));
    private final ArrayList<PkgInfo> packages;
    String title = "Javazon delivery tracker";

    /**
     * Constructor to initialize the package list from the Main class
     *
     * @param packages
     */
    public MainMenu(ArrayList<PkgInfo> packages) {

        this.packages = packages;

    }

    /**
     * Displays the text menu onto the screen and sorts the array by date
     */
    public void displayMenu() {

        //Uses comparator class to sort array
        packages.sort(new SortByDate());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = LocalDateTime.now().format(formatter);

        //Prints rectangle around title
        for (int i = 0; i <= title.length() + 3; i++) {

            System.out.print('#');

        }

        System.out.println("\n# " + title + " #");

        for (int i = 0; i <= title.length() + 3; i++) {

            System.out.print('#');

        }

        System.out.println();
        System.out.println("Today's date: " + formatDateTime + "\n");

        for (int i = 0; i < choices.size(); i++) {

            System.out.println((i + 1) + ". " + choices.get(i));

        }

        takeInput();

    }

    /**
     * Takes input from the user and carries out the desired action
     */
    public void takeInput() {

        //Initial main menu choice with validation
        Scanner mainInput = new Scanner(System.in);
        System.out.print("Choose an option by entering 1-7: ");
        int option = mainInput.nextInt();

        while (option < 1 || option > 7) {

            System.out.println("Please enter a valid choice between 1-7: ");
            option = mainInput.nextInt();

        }

        //Choices for each menu choice
        if (option == 1) {

            if (packages.size() == 0) System.out.println("\nNo packages to display.\n");
            else for (int i = 0; i < packages.size(); i++) {

                System.out.println();
                System.out.println("Package #" + (i + 1));
                System.out.println(packages.get(i));

            }
            displayMenu();

        } else if (option == 2) { //Add

            int dateValid = 0;
            LocalDateTime ld = LocalDateTime.now();

            Scanner addInput = new Scanner(System.in);
            System.out.print("Enter the name of the package: ");
            String name = addInput.nextLine();
            while (name.equals("")) {

                System.out.print("Name cannot be blank : ");
                name = addInput.nextLine();

            }

            System.out.print("Enter the notes of the package: ");
            String notes = addInput.nextLine();

            System.out.print("Enter the price of the package (in dollars): ");
            Double price = addInput.nextDouble();
            while (price < 0) {

                System.out.print("Please enter a non-negative number: ");
                price = addInput.nextDouble();

            }

            System.out.print("Enter the weight of the package (in kg): ");
            Double weight = addInput.nextDouble();
            while (weight < 0) {

                System.out.print("Please enter a non-negative number: ");
                weight = addInput.nextDouble();

            }

            //Uses try/catch to make sure user only inputs a valid date
            while (dateValid == 0) {

                System.out.print("Enter the year of the expected delivery date: ");
                int year = addInput.nextInt();
                while (year < 0) {

                    System.out.print("Please enter a non-negative value: ");
                    year = addInput.nextInt();

                }

                System.out.print("Enter the month of the expected delivery date (1-12): ");
                int month = addInput.nextInt();
                while (month < 1 || month > 12) {

                    System.out.print("Please enter a valid month between 1-12: ");
                    month = addInput.nextInt();

                }

                System.out.print("Enter the day of the expected delivery date (1-28/29/30/31): ");
                int day = addInput.nextInt();
                while (day < 1 || day > 31) {

                    System.out.print("Please enter a valid day between 1-28/29/30/31: ");
                    day = addInput.nextInt();

                }

                System.out.print("Enter the hour of the expected delivery date (0-23): ");
                int hour = addInput.nextInt();
                while (hour < 0 || hour > 23) {

                    System.out.print("Please enter a valid day between 0-23: ");
                    hour = addInput.nextInt();

                }

                System.out.print("Enter the minute of the expected delivery date (0-59): ");
                int minute = addInput.nextInt();
                while (minute < 0 || minute > 59) {

                    System.out.print("Please enter a valid minute between 0-59: ");
                    minute = addInput.nextInt();

                }

                try {

                    ld = LocalDateTime.of(year, month, day, hour, minute);
                    dateValid = 1;

                } catch (DateTimeException e) {

                    System.out.print("Invalid date. Try again\n");

                }
            }

            packages.add(new PkgInfo(name, notes, price, weight, ld));
            System.out.println();
            System.out.println(name + " added.\n");
            displayMenu();

        } else if (option == 3) { //Remove

            if (packages.size() == 0) {

                System.out.println("No packages to display\n");
                displayMenu();

            } else for (int i = 0; i < packages.size(); i++) {

                System.out.println();
                System.out.println("Package #" + (i + 1));
                System.out.println(packages.get(i));

            }

            System.out.print("Enter the item number you want to remove (0 to cancel): ");
            Scanner remInput = new Scanner(System.in);
            int remPack = remInput.nextInt();
            while (remPack < 0 || remPack > packages.size()) {

                System.out.print("Please enter a valid option: ");
                remPack = remInput.nextInt();

            }
            if (remPack == 0) {

                System.out.println("Delete cancelled\n");

            } else {

                String remName = packages.get(remPack - 1).getName();
                packages.remove(remPack - 1);
                System.out.println(remName + " removed.\n");

            }
            displayMenu();

        } else if (option == 4) { //Overdue packages

            if (packages.size() == 0) {

                System.out.println("No packages to display\n");
                displayMenu();

            }
            boolean packagesExist = false;
            int pckNum = 0;
            for (PkgInfo aPackage : packages) {

                if (aPackage.getDeliveryDate().isBefore(LocalDateTime.now()) && !aPackage.isDelivered()) {

                    System.out.println();
                    pckNum++;
                    System.out.println("Package #" + pckNum);
                    System.out.println(aPackage);
                    packagesExist = true;

                }

            }

            if (!packagesExist) System.out.println("No overdue packages.\n");
            displayMenu();

        } else if (option == 5) { //Upcoming packages

            if (packages.size() == 0) {

                System.out.println("No packages to display\n");
                displayMenu();

            }
            boolean packagesExist = false;
            int pckNum = 0;
            for (PkgInfo aPackage : packages) {

                if (aPackage.getDeliveryDate().isAfter(LocalDateTime.now()) && !aPackage.isDelivered()) {

                    System.out.println();
                    pckNum++;
                    System.out.println("Package #" + pckNum);
                    System.out.println(aPackage);
                    packagesExist = true;

                }

            }

            if (!packagesExist) System.out.println("\nNo upcoming packages.\n");
            displayMenu();

        } else if (option == 6) { //Delivery status

            if (packages.size() == 0) {

                System.out.println("No packages to display\n");
                displayMenu();

            }
            boolean packagesExist = false;
            ArrayList<Integer> indexes = new ArrayList<>();
            for (int i = 0; i < packages.size(); i++) {

                if (!packages.get(i).isDelivered()) {

                    indexes.add(i);
                    System.out.println();
                    System.out.println("Package #" + indexes.size());
                    System.out.println(packages.get(i));
                    packagesExist = true;

                }

            }
            if (!packagesExist) System.out.println("No undelivered packages.");
            else {

                Scanner pc = new Scanner(System.in);
                System.out.print("Select a package to mark as completed (0 to cancel): ");
                int pkgTBDel = pc.nextInt();
                while (pkgTBDel < 0 || pkgTBDel >= indexes.size() + 1) {

                    System.out.print("Please select a valid package(0 to cancel): ");
                    pkgTBDel = pc.nextInt();

                }
                if (pkgTBDel == 0) System.out.println("Cancelled.");
                else {

                    packages.get(indexes.get(pkgTBDel - 1)).setDelivered();
                    System.out.println(packages.get(indexes.get(pkgTBDel - 1)).getName() + " has been delivered.\n");

                }

            }

            displayMenu();

        } else {

            System.out.println("\nPackages saved. Goodbye!");
            return;
        }

    }
}
