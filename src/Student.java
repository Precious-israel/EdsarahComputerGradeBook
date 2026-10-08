package src;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single student in the Ed Sarah Computer Grade Book.
 * Demonstrates: classes, variables, expressions, and encapsulation.
 */
public class Student {

    // Instance variables (private = encapsulation)
    private int studentId;          // int variable
    private String name;            // String variable
    private List<Double> grades;    // Collection: ArrayList of grades
    private boolean active;         // boolean variable

    // Constant (final) for a fixed value
    private static final double MAX_GRADE = 100.0;

    // Constructor
    public Student(int studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.grades = new ArrayList<>();   // Initialize ArrayList
        this.active = true;
    }

    // ---- Getters ----
    public int getStudentId() { return studentId; }
    public String getName()     { return name; }
    public List<Double> getGrades() { return grades; }
    public boolean isActive()   { return active; }

    // ---- Setters ----
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name.trim();
        }
    }

    public void setActive(boolean active) { this.active = active; }

    // ---- Grade methods ----

    /** Adds a grade if it is within valid range (expression + conditional). */
    public boolean addGrade(double grade) {
        if (grade >= 0.0 && grade <= MAX_GRADE) {   // conditional
            grades.add(grade);
            return true;
        }
        return false;
    }

    /** Calculates the average of all grades (expression + loop). */
    public double calculateAverage() {
        if (grades.isEmpty()) return 0.0;            // conditional

        double sum = 0.0;
        for (double g : grades) {                    // enhanced for loop
            sum += g;                                // expression
        }
        return sum / grades.size();                  // expression
    }

    /** Returns the letter grade based on the numeric average. */
    public String getLetterGrade() {
        double avg = calculateAverage();

        // if / else if / else conditional chain
        if (avg >= 90.0)      return "A";
        else if (avg >= 80.0) return "B";
        else if (avg >= 70.0) return "C";
        else if (avg >= 60.0) return "D";
        else                  return "F";
    }

    /** Returns a formatted string representation (expression: String.format). */
    @Override
    public String toString() {
        return String.format(
            "ID: %-5d | Name: %-20s | Grades: %-20s | Avg: %6.2f | Letter: %s",
            studentId,
            name,
            grades.toString(),
            calculateAverage(),
            getLetterGrade()
        );
    }

    /** Returns a CSV line for file storage. */
    public String toCsv() {
        StringBuilder sb = new StringBuilder();
        sb.append(studentId).append(",").append(name).append(",");
        for (int i = 0; i < grades.size(); i++) {
            sb.append(grades.get(i));
            if (i < grades.size() - 1) sb.append(";");
        }
        return sb.toString();
    }

    /** Creates a Student from a CSV line. */
    public static Student fromCsv(String line) {
        String[] parts = line.split(",", 3);
        if (parts.length < 2) return null;

        int id = Integer.parseInt(parts[0].trim());
        String name = parts[1].trim();
        Student s = new Student(id, name);

        if (parts.length == 3 && !parts[2].isEmpty()) {
            for (String g : parts[2].split(";")) {
                try {
                    s.addGrade(Double.parseDouble(g.trim()));
                } catch (NumberFormatException e) {
                    // skip invalid grade
                }
            }
        }
        return s;
    }
}
