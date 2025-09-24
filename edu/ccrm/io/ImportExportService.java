package edu.ccrm.io;

import edu.ccrm.domain.*;
import edu.ccrm.config.AppConfig;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

/**
 * Import/Export service using NIO.2 and Streams
 * Demonstrates modern Java I/O, Streams, and CSV processing
 */
public class ImportExportService {
    private final AppConfig config;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    public ImportExportService() {
        this.config = AppConfig.getInstance();
    }
    
    /**
     * Import students from CSV file using NIO.2 and Streams
     */
    public List<Student> importStudents(String fileName) throws IOException {
        Path filePath = Paths.get(config.getDataFolder(), fileName);
        
        if (!Files.exists(filePath)) {
            throw new IOException("File not found: " + filePath);
        }
        
        List<Student> students = new ArrayList<>();
        
        // Using try-with-resources and Streams
        try (Stream<String> lines = Files.lines(filePath)) {
            students = lines
                .skip(1) // Skip header
                .filter(line -> !line.trim().isEmpty())
                .map(this::parseStudentFromCsv)
                .filter(Objects::nonNull)
                .toList(); // Java 16+ toList() method
        }
        
        System.out.println("Imported " + students.size() + " students from " + fileName);
        return students;
    }
    
    /**
     * Import courses from CSV file
     */
    public List<Course> importCourses(String fileName) throws IOException {
        Path filePath = Paths.get(config.getDataFolder(), fileName);
        
        if (!Files.exists(filePath)) {
            throw new IOException("File not found: " + filePath);
        }
        
        List<Course> courses = new ArrayList<>();
        
        try (Stream<String> lines = Files.lines(filePath)) {
            courses = lines
                .skip(1) // Skip header
                .filter(line -> !line.trim().isEmpty())
                .map(this::parseCourseFromCsv)
                .filter(Objects::nonNull)
                .toList();
        }
        
        System.out.println("Imported " + courses.size() + " courses from " + fileName);
        return courses;
    }
    
    /**
     * Export students to CSV file
     */
    public void exportStudents(List<Student> students, String fileName) throws IOException {
        Path filePath = Paths.get(config.getExportFolder(), fileName);
        
        List<String> lines = new ArrayList<>();
        lines.add("ID,RegNo,FullName,Email,Active,EnrolledCourses,GPA,CreatedAt");
        
        // Using streams to convert students to CSV lines
        lines.addAll(students.stream()
            .map(this::studentToCsv)
            .toList());
        
        Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Exported " + students.size() + " students to " + filePath);
    }
    
    /**
     * Export courses to CSV file
     */
    public void exportCourses(List<Course> courses, String fileName) throws IOException {
        Path filePath = Paths.get(config.getExportFolder(), fileName);
        
        List<String> lines = new ArrayList<>();
        lines.add("Code,Title,Credits,Instructor,Semester,Department,Active,CreatedAt");
        
        lines.addAll(courses.stream()
            .map(this::courseToCsv)
            .toList());
        
        Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Exported " + courses.size() + " courses to " + filePath);
    }
    
    /**
     * Parse student from CSV line
     */
    private Student parseStudentFromCsv(String csvLine) {
        try {
            String[] parts = csvLine.split(",");
            if (parts.length >= 4) {
                String id = parts[0].trim();
                String regNo = parts[1].trim();
                String fullName = parts[2].trim();
                String email = parts[3].trim();
                
                Student student = new Student(id, regNo, fullName, email);
                
                // Set active status if present
                if (parts.length > 4) {
                    student.setActive(Boolean.parseBoolean(parts[4].trim()));
                }
                
                return student;
            }
        } catch (Exception e) {
            System.err.println("Error parsing student line: " + csvLine + " - " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Parse course from CSV line
     */
    private Course parseCourseFromCsv(String csvLine) {
        try {
            String[] parts = csvLine.split(",");
            if (parts.length >= 6) {
                String code = parts[0].trim();
                String title = parts[1].trim();
                int credits = Integer.parseInt(parts[2].trim());
                String instructor = parts[3].trim();
                Semester semester = Semester.fromString(parts[4].trim());
                String department = parts[5].trim();
                
                return new Course.Builder()
                    .setCode(code)
                    .setTitle(title)
                    .setCredits(credits)
                    .setInstructor(instructor)
                    .setSemester(semester)
                    .setDepartment(department)
                    .build();
            }
        } catch (Exception e) {
            System.err.println("Error parsing course line: " + csvLine + " - " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Convert student to CSV line
     */
    private String studentToCsv(Student student) {
        return String.join(",",
            student.getId(),
            student.getRegNo(),
            "\"" + student.getFullName() + "\"", // Quote names that might have commas
            student.getEmail(),
            String.valueOf(student.isActive()),
            String.valueOf(student.getEnrolledCourses().size()),
            String.format("%.2f", student.calculateGPA()),
            student.getCreatedAt().format(dateFormatter)
        );
    }
    
    /**
     * Convert course to CSV line
     */
    private String courseToCsv(Course course) {
        return String.join(",",
            course.getCode(),
            "\"" + course.getTitle() + "\"",
            String.valueOf(course.getCredits()),
            course.getInstructor(),
            course.getSemester().name(),
            course.getDepartment(),
            String.valueOf(course.isActive()),
            course.getCreatedAt().format(dateFormatter)
        );
    }
}