package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.service.*;
import edu.ccrm.domain.*;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.util.FileUtils;
import edu.ccrm.exceptions.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Main CLI menu system
 * Demonstrates switch statements, loops, exception handling, and user interaction
 */
public class MenuSystem {
    private final Scanner scanner;
    private final StudentService studentService;
    private final CourseService courseService;
    private final ImportExportService importExportService;
    private final AppConfig config;
    private boolean running;
    
    public MenuSystem() {
        this.scanner = new Scanner(System.in);
        this.studentService = new StudentService();
        this.courseService = new CourseService();
        this.importExportService = new ImportExportService();
        this.config = AppConfig.getInstance();
        this.running = true;
        
        initializeSampleData();
    }
    
    /**
     * Main menu loop with enhanced switch statement (Java 14+)
     */
    public void start() {
        System.out.println(config.getAppInfo());
        System.out.println(config.getJavaPlatformComparison());
        
        while (running) {
            try {
                displayMainMenu();
                int choice = getIntInput("Enter your choice: ");
                
                // Using enhanced switch expression (Java 14+)
                switch (choice) {
                    case 1 -> handleStudentManagement();
                    case 2 -> handleCourseManagement();
                    case 3 -> handleEnrollmentManagement();
                    case 4 -> handleGradeManagement();
                    case 5 -> handleImportExport();
                    case 6 -> handleBackupOperations();
                    case 7 -> handleReports();
                    case 8 -> {
                        System.out.println("Thank you for using CCRM!");
                        running = false;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
                
                if (running) {
                    System.out.println("\\nPress Enter to continue...");
                    scanner.nextLine();
                }
                
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\\n" + "=".repeat(50));
        System.out.println("    Campus Course & Records Manager (CCRM)");
        System.out.println("=".repeat(50));
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment Management");
        System.out.println("4. Grade Management");
        System.out.println("5. Import/Export Data");
        System.out.println("6. Backup Operations");
        System.out.println("7. Reports & Analytics");
        System.out.println("8. Exit");
        System.out.println("=".repeat(50));
    }
    
    private void handleStudentManagement() {
        System.out.println("\\n--- Student Management ---");
        System.out.println("1. Add Student");
        System.out.println("2. List All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Student");
        System.out.println("5. Deactivate Student");
        System.out.println("6. View Student Profile");
        
        int choice = getIntInput("Enter choice: ");
        
        switch (choice) {
            case 1 -> addStudent();
            case 2 -> listAllStudents();
            case 3 -> searchStudent();
            case 4 -> updateStudent();
            case 5 -> deactivateStudent();
            case 6 -> viewStudentProfile();
            default -> System.out.println("Invalid choice.");
        }
    }
    
    private void addStudent() {
        try {
            System.out.println("\\n--- Add New Student ---");
            String id = getStringInput("Student ID: ");
            String regNo = getStringInput("Registration Number (YYYY-DEPT-NNNN): ");
            String fullName = getStringInput("Full Name: ");
            String email = getStringInput("Email: ");
            
            Student student = new Student(id, regNo, fullName, email);
            studentService.addStudent(student);
            
            System.out.println("Student added successfully!");
            System.out.println(student);
            
        } catch (Exception e) {
            System.err.println("Error adding student: " + e.getMessage());
        }
    }
    
    private void listAllStudents() {
        System.out.println("\\n--- All Students ---");
        List<Student> students = studentService.findAll();
        
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        
        // Using enhanced for loop
        for (Student student : students) {
            System.out.println(student);
        }
        
        System.out.println("\\nTotal students: " + students.size());
    }
    
    private void handleCourseManagement() {
        System.out.println("\\n--- Course Management ---");
        System.out.println("1. Add Course");
        System.out.println("2. List All Courses");
        System.out.println("3. Search Courses");
        System.out.println("4. Update Course");
        System.out.println("5. Deactivate Course");
        
        int choice = getIntInput("Enter choice: ");
        
        switch (choice) {
            case 1 -> addCourse();
            case 2 -> listAllCourses();
            case 3 -> searchCourses();
            case 4 -> updateCourse();
            case 5 -> deactivateCourse();
            default -> System.out.println("Invalid choice.");
        }
    }
    
    private void addCourse() {
        try {
            System.out.println("\\n--- Add New Course ---");
            String code = getStringInput("Course Code (e.g., CS101-A): ");
            String title = getStringInput("Course Title: ");
            int credits = getIntInput("Credits (1-6): ");
            String instructor = getStringInput("Instructor: ");
            System.out.println("Available semesters: SPRING, SUMMER, FALL, WINTER");
            String semesterStr = getStringInput("Semester: ");
            String department = getStringInput("Department: ");
            
            Semester semester = Semester.fromString(semesterStr);
            
            Course course = new Course.Builder()
                .setCode(code)
                .setTitle(title)
                .setCredits(credits)
                .setInstructor(instructor)
                .setSemester(semester)
                .setDepartment(department)
                .build();
                
            courseService.addCourse(course);
            System.out.println("Course added successfully!");
            System.out.println(course);
            
        } catch (Exception e) {
            System.err.println("Error adding course: " + e.getMessage());
        }
    }
    
    private void listAllCourses() {
        System.out.println("\\n--- All Courses ---");
        List<Course> courses = courseService.findAll();
        
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        
        courses.forEach(System.out::println); // Lambda expression
        System.out.println("\\nTotal courses: " + courses.size());
    }
    
    // Utility methods
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    private void initializeSampleData() {
        // Add some sample students
        studentService.addStudent(new Student("S001", "2023-CS-0001", "John Doe", "john.doe@email.com"));
        studentService.addStudent(new Student("S002", "2023-CS-0002", "Jane Smith", "jane.smith@email.com"));
        
        // Add some sample courses
        Course course1 = new Course.Builder()
            .setCode("CS101-A")
            .setTitle("Introduction to Programming")
            .setCredits(3)
            .setInstructor("Dr. Johnson")
            .setSemester(Semester.FALL)
            .setDepartment("Computer Science")
            .build();
            
        Course course2 = new Course.Builder()
            .setCode("MATH201-B")
            .setTitle("Calculus II")
            .setCredits(4)
            .setInstructor("Prof. Davis")
            .setSemester(Semester.SPRING)
            .setDepartment("Mathematics")
            .build();
            
        courseService.addCourse(course1);
        courseService.addCourse(course2);
    }
    
    // Placeholder methods for other menu options
    private void searchStudent() { System.out.println("Search student functionality - placeholder"); }
    private void updateStudent() { System.out.println("Update student functionality - placeholder"); }
    private void deactivateStudent() { System.out.println("Deactivate student functionality - placeholder"); }
    private void viewStudentProfile() { System.out.println("View student profile functionality - placeholder"); }
    private void searchCourses() { System.out.println("Search courses functionality - placeholder"); }
    private void updateCourse() { System.out.println("Update course functionality - placeholder"); }
    private void deactivateCourse() { System.out.println("Deactivate course functionality - placeholder"); }
    private void handleEnrollmentManagement() { System.out.println("Enrollment management functionality - placeholder"); }
    private void handleGradeManagement() { System.out.println("Grade management functionality - placeholder"); }
    private void handleImportExport() { System.out.println("Import/Export functionality - placeholder"); }
    private void handleBackupOperations() { System.out.println("Backup operations functionality - placeholder"); }
    private void handleReports() { System.out.println("Reports functionality - placeholder"); }
}