import java.util.List;
import java.util.Scanner;

/**
 * Main entry point for the Ed Sarah Computer Student Grade Book.
 * Demonstrates: static main, while loop, switch, functions, exception handling.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Gradebook gradebook = new Gradebook();
    private static boolean running = true;

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   EDSARAH COMPUTER STUDENT GRADE BOOK");
        System.out.println("==================================================");

        // Load existing data from file on startup
        FileManager.loadFromFile(gradebook);

        // Main menu loop
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> removeStudent();
                case 3 -> updateStudent();
                case 4 -> addGrade();
                case 5 -> listStudents();
                case 6 -> searchStudents();
                case 7 -> showStatistics();
                case 8 -> saveAndExit();
                default -> System.out.println("  Invalid choice. Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("+-------------- MENU --------------+");
        System.out.println("| 1. Add Student                   |");
        System.out.println("| 2. Remove Student                |");
        System.out.println("| 3. Update Student Name           |");
        System.out.println("| 4. Add Grade to Student          |");
        System.out.println("| 5. List All Students             |");
        System.out.println("| 6. Search Student by Name        |");
        System.out.println("| 7. Show Statistics               |");
        System.out.println("| 8. Save & Exit                   |");
        System.out.println("+----------------------------------+");
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("  Invalid number. Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("  Invalid number. Please enter a valid decimal.");
            }
        }
    }

    private static void addStudent() {
        System.out.println();
        System.out.println("--- Add Student ---");
        int id = readInt("Enter student ID: ");
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("  Name cannot be empty.");
            return;
        }

        if (gradebook.addStudent(id, name)) {
            System.out.println("  [OK] Student added successfully.");
        } else {
            System.out.println("  [X] A student with that ID already exists.");
        }
    }

    private static void removeStudent() {
        System.out.println();
        System.out.println("--- Remove Student ---");
        int id = readInt("Enter student ID to remove: ");
        if (gradebook.removeStudent(id)) {
            System.out.println("  [OK] Student removed.");
        } else {
            System.out.println("  [X] Student not found.");
        }
    }

    private static void updateStudent() {
        System.out.println();
        System.out.println("--- Update Student Name ---");
        int id = readInt("Enter student ID: ");
        Student s = gradebook.findStudent(id);
        if (s == null) {
            System.out.println("  [X] Student not found.");
            return;
        }
        System.out.println("  Current name: " + s.getName());
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine().trim();
        if (gradebook.updateStudentName(id, newName)) {
            System.out.println("  [OK] Name updated.");
        }
    }

    private static void addGrade() {
        System.out.println();
        System.out.println("--- Add Grade ---");
        int id = readInt("Enter student ID: ");
        Student s = gradebook.findStudent(id);
        if (s == null) {
            System.out.println("  [X] Student not found.");
            return;
        }
        double grade = readDouble("Enter grade (0-100): ");
        if (gradebook.addGrade(id, grade)) {
            System.out.println("  [OK] Grade added. New average: "
                    + String.format("%.2f", s.calculateAverage())
                    + " (" + s.getLetterGrade() + ")");
        } else {
            System.out.println("  [X] Invalid grade. Must be between 0 and 100.");
        }
    }

    private static void listStudents() {
        gradebook.listAllStudents();
    }

    private static void searchStudents() {
        System.out.println();
        System.out.println("--- Search Student ---");
        System.out.print("Enter name (or part of name): ");
        String query = scanner.nextLine().trim();
        List<Student> results = gradebook.searchByName(query);
        if (results.isEmpty()) {
            System.out.println("  No matching students found.");
        } else {
            System.out.println("  Found " + results.size() + " result(s):");
            for (Student s : results) {
                System.out.println("  " + s);
            }
        }
    }

    private static void showStatistics() {
        System.out.println();
        System.out.println("--- Class Statistics ---");
        System.out.println("  Total students: " + gradebook.size());
        System.out.printf("  Class average:  %.2f%n", gradebook.calculateClassAverage());
        System.out.println("  " + gradebook.getGradeDistribution());
    }

    private static void saveAndExit() {
        System.out.println();
        System.out.println("--- Saving Data ---");
        FileManager.saveToFile(gradebook.getAllStudents());
        running = false;
        System.out.println("  Thank you for using Ed Sarah Computer Grade Book. Goodbye!");
        scanner.close();
    }
}