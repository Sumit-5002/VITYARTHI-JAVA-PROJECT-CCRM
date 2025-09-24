package edu.ccrm.domain;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Student class extending Person
 * Demonstrates inheritance and polymorphism
 */
public class Student extends Person {
    private final String regNo;
    private final Set<String> enrolledCourses;
    private final Map<String, Grade> grades;
    private LocalDateTime enrollmentDate;
    
    public Student(String id, String regNo, String fullName, String email) {
        super(id, fullName, email);
        this.regNo = regNo;
        this.enrolledCourses = new HashSet<>();
        this.grades = new HashMap<>();
        this.enrollmentDate = LocalDateTime.now();
    }
    
    @Override
    public String getRole() {
        return "STUDENT";
    }
    
    public String getRegNo() { return regNo; }
    public Set<String> getEnrolledCourses() { return new HashSet<>(enrolledCourses); }
    public Map<String, Grade> getGrades() { return new HashMap<>(grades); }
    public LocalDateTime getEnrollmentDate() { return enrollmentDate; }
    
    public void enrollCourse(String courseCode) {
        enrolledCourses.add(courseCode);
    }
    
    public void unenrollCourse(String courseCode) {
        enrolledCourses.remove(courseCode);
        grades.remove(courseCode);
    }
    
    public void assignGrade(String courseCode, Grade grade) {
        if (enrolledCourses.contains(courseCode)) {
            grades.put(courseCode, grade);
        }
    }
    
    public double calculateGPA() {
        if (grades.isEmpty()) return 0.0;
        
        return grades.values().stream()
                .mapToDouble(Grade::getGradePoints)
                .average()
                .orElse(0.0);
    }
    
    @Override
    public String toString() {
        return String.format("Student{regNo='%s', name='%s', courses=%d, GPA=%.2f}", 
                           regNo, fullName, enrolledCourses.size(), calculateGPA());
    }
}