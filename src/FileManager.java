import java.io.*;
import java.util.List;

/**
 * Handles reading and writing student data to a text file.
 * Demonstrates: Java File I/O (File, BufferedReader, PrintWriter), exception handling.
 */
public class FileManager {

    private static final String DATA_FILE = "data/students.txt";

    /** Saves all students to the data file (write to file). */
    public static void saveToFile(List<Student> students) {
        // Ensure the data directory exists
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Student s : students) {
                writer.println(s.toCsv());
            }
            System.out.println("  ✔ Data saved to " + DATA_FILE);
        } catch (IOException e) {
            System.out.println("  ✘ Error saving file: " + e.getMessage());
        }
    }

    /** Loads students from the data file (read from file). */
    public static void loadFromFile(Gradebook gradebook) {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("  No existing data file found. Starting fresh.");
            return;
        }

        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {   // while loop
                if (line.trim().isEmpty()) continue;
                Student s = Student.fromCsv(line);
                if (s != null) {
                    // Re-add using addStudent to keep both collections in sync
                    gradebook.addStudent(s.getStudentId(), s.getName());
                    Student stored = gradebook.findStudent(s.getStudentId());
                    if (stored != null) {
                        for (double g : s.getGrades()) {
                            stored.addGrade(g);
                        }
                    }
                    count++;
                }
            }
            System.out.println("  ✔ Loaded " + count + " student(s) from " + DATA_FILE);
        } catch (IOException e) {
            System.out.println("  ✘ Error reading file: " + e.getMessage());
        }
    }
}