package edu.ccrm.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Enrollment entity representing student-course relationship
 * Demonstrates composition and business logic
 */
public class Enrollment {
    private final String studentId;
    private final String courseCode;
    private final LocalDateTime enrollmentDate;
    private Grade grade;
    private boolean active;
    
    // Static nested class for enrollment status
    public static class EnrollmentStatus {
        public static final String ENROLLED = "ENROLLED";
        public static final String COMPLETED = "COMPLETED";
        public static final String DROPPED = "DROPPED";
        public static final String WITHDRAWN = "WITHDRAWN";
    }
    
    public Enrollment(String studentId, String courseCode) {
        this.studentId = Objects.requireNonNull(studentId, "Student ID cannot be null");
        this.courseCode = Objects.requireNonNull(courseCode, "Course code cannot be null");
        this.enrollmentDate = LocalDateTime.now();
        this.active = true;
    }
    
    // Getters
    public String getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }
    public LocalDateTime getEnrollmentDate() { return enrollmentDate; }
    public Grade getGrade() { return grade; }
    public boolean isActive() { return active; }
    
    // Business methods
    public void assignGrade(Grade grade) {
        this.grade = grade;
    }
    
    public void deactivate() {
        this.active = false;
    }
    
    public String getStatus() {
        if (!active) return EnrollmentStatus.DROPPED;
        if (grade != null) return EnrollmentStatus.COMPLETED;
        return EnrollmentStatus.ENROLLED;
    }
    
    @Override
    public String toString() {
        return String.format("Enrollment{student='%s', course='%s', grade=%s, status='%s'}", 
                           studentId, courseCode, grade, getStatus());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Enrollment that = (Enrollment) obj;
        return Objects.equals(studentId, that.studentId) &&
               Objects.equals(courseCode, that.courseCode);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseCode);
    }
}