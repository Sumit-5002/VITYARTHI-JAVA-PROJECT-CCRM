package edu.ccrm.service;

import edu.ccrm.domain.*;
import edu.ccrm.util.Validators;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Student Service implementing business logic
 * Demonstrates polymorphism, streams, and lambda expressions
 */
public class StudentService implements DataService<Student> {
    private final Map<String, Student> students;
    private final Map<String, Enrollment> enrollments;
    
    public StudentService() {
        this.students = new HashMap<>();
        this.enrollments = new HashMap<>();
    }
    
    // CRUD Operations
    public void addStudent(Student student) {
        validateData(student);
        students.put(student.getId(), student);
    }
    
    public void updateStudent(Student student) {
        if (!exists(student.getId())) {
            throw new IllegalArgumentException("Student not found: " + student.getId());
        }
        validateData(student);
        students.put(student.getId(), student);
    }
    
    public void deactivateStudent(String studentId) {
        Student student = findById(studentId);
        if (student != null) {
            student.setActive(false);
        }
    }
    
    // Enrollment Management
    public void enrollStudent(String studentId, String courseCode) throws Exception {
        Student student = findById(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Student not found: " + studentId);
        }
        
        // Business rule: Check max credits per semester (example: 24 credits)
        int currentCredits = calculateCurrentCredits(studentId);
        if (currentCredits >= 24) {
            throw new Exception("Maximum credit limit exceeded for student: " + studentId);
        }
        
        // Check for duplicate enrollment
        String enrollmentKey = studentId + "-" + courseCode;
        if (enrollments.containsKey(enrollmentKey)) {
            throw new Exception("Student already enrolled in course: " + courseCode);
        }
        
        // Create enrollment
        Enrollment enrollment = new Enrollment(studentId, courseCode);
        enrollments.put(enrollmentKey, enrollment);
        student.enrollCourse(courseCode);
    }
    
    public void unenrollStudent(String studentId, String courseCode) {
        String enrollmentKey = studentId + "-" + courseCode;
        Enrollment enrollment = enrollments.get(enrollmentKey);
        if (enrollment != null) {
            enrollment.deactivate();
            Student student = findById(studentId);
            if (student != null) {
                student.unenrollCourse(courseCode);
            }
        }
    }
    
    public void assignGrade(String studentId, String courseCode, Grade grade) {
        String enrollmentKey = studentId + "-" + courseCode;
        Enrollment enrollment = enrollments.get(enrollmentKey);
        if (enrollment != null && enrollment.isActive()) {
            enrollment.assignGrade(grade);
            Student student = findById(studentId);
            if (student != null) {
                student.assignGrade(courseCode, grade);
            }
        }
    }
    
    private int calculateCurrentCredits(String studentId) {
        // Mock implementation - would integrate with CourseService
        return 18; // Example current credits
    }
    
    // Interface implementations
    @Override
    public String getId() {
        return "StudentService";
    }
    
    @Override
    public void save() {
        // Implementation would persist to file/database
        System.out.println("Saving " + students.size() + " students...");
    }
    
    @Override
    public void delete() {
        students.clear();
        enrollments.clear();
    }
    
    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students.values());
    }
    
    @Override
    public List<Student> findBy(Predicate<Student> criteria) {
        return students.values().stream()
                .filter(criteria)
                .collect(Collectors.toList());
    }
    
    @Override
    public Student findById(String id) {
        return students.get(id);
    }
    
    @Override
    public void validateData(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (!Validators.isValidEmail(student.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        // Additional validations...
    }
    
    @Override
    public void backup() {
        System.out.println("Backing up student data...");
    }
    
    @Override
    public void restore() {
        System.out.println("Restoring student data...");
    }
    
    // Utility methods using streams and lambdas
    public List<Student> getTopStudents(int limit) {
        return students.values().stream()
                .filter(Student::isActive)
                .sorted((s1, s2) -> Double.compare(s2.calculateGPA(), s1.calculateGPA()))
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    public double getAverageGPA() {
        return students.values().stream()
                .filter(Student::isActive)
                .mapToDouble(Student::calculateGPA)
                .average()
                .orElse(0.0);
    }
    
    public Map<String, Long> getGradeDistribution() {
        return enrollments.values().stream()
                .filter(e -> e.getGrade() != null)
                .collect(Collectors.groupingBy(
                    e -> e.getGrade().name(),
                    Collectors.counting()
                ));
    }
}