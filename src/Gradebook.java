import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the collection of students.
 * Demonstrates: Java Collection Framework (ArrayList + HashMap), loops, functions.
 */
public class Gradebook {

    // Collection: ArrayList stores the ordered list of students
    private List<Student> students;

    // Collection: HashMap provides fast lookup by student ID
    private Map<Integer, Student> studentMap;

    public Gradebook() {
        this.students = new ArrayList<>();
        this.studentMap = new HashMap<>();
    }

    // ---- Core operations ----

    /** Adds a student. Returns false if ID already exists (conditional). */
    public boolean addStudent(int id, String name) {
        if (studentMap.containsKey(id)) {      // conditional
            return false;
        }
        Student s = new Student(id, name);
        students.add(s);                       // ArrayList.add()
        studentMap.put(id, s);                 // HashMap.put()
        return true;
    }

    /** Removes a student by ID. */
    public boolean removeStudent(int id) {
        Student s = studentMap.remove(id);     // HashMap.remove()
        if (s != null) {
            students.remove(s);                // ArrayList.remove()
            return true;
        }
        return false;
    }

    /** Updates a student's name. */
    public boolean updateStudentName(int id, String newName) {
        Student s = studentMap.get(id);        // HashMap.get()
        if (s != null) {
            s.setName(newName);
            return true;
        }
        return false;
    }

    /** Adds a grade to a student. */
    public boolean addGrade(int id, double grade) {
        Student s = studentMap.get(id);
        if (s != null) {
            return s.addGrade(grade);
        }
        return false;
    }

    /** Finds a student by ID (HashMap fast lookup). */
    public Student findStudent(int id) {
        return studentMap.get(id);
    }

    /** Lists all students (for loop + ArrayList iteration). */
    public void listAllStudents() {
        if (students.isEmpty()) {
            System.out.println("  No students in the gradebook yet.");
            return;
        }
        System.out.println("\n--- All Students ---");
        for (Student s : students) {           // enhanced for loop
            System.out.println("  " + s);
        }
        System.out.println("  Total: " + students.size() + " student(s)");
    }

    /** Searches students by name fragment (String method + loop). */
    public List<Student> searchByName(String query) {
        List<Student> results = new ArrayList<>();
        String q = query.toLowerCase();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(q)) {  // conditional
                results.add(s);
            }
        }
        return results;
    }

    /** Calculates the class average across all students (loop + expression). */
    public double calculateClassAverage() {
        if (students.isEmpty()) return 0.0;
        double total = 0.0;
        for (Student s : students) {
            total += s.calculateAverage();
        }
        return total / students.size();
    }

    /** Returns a summary string of grade distribution (HashMap counting). */
    public String getGradeDistribution() {
        Map<String, Integer> distribution = new HashMap<>();
        for (Student s : students) {
            String letter = s.getLetterGrade();
            distribution.put(letter, distribution.getOrDefault(letter, 0) + 1);
        }
        StringBuilder sb = new StringBuilder("Grade Distribution: ");
        String[] letters = {"A", "B", "C", "D", "F"};
        for (String l : letters) {
            sb.append(l).append("=").append(distribution.getOrDefault(l, 0)).append("  ");
        }
        return sb.toString();
    }

    // ---- File I/O support ----

    public List<Student> getAllStudents() {
        return students;
    }

    public void clear() {
        students.clear();
        studentMap.clear();
    }

    public int size() { return students.size(); }
}