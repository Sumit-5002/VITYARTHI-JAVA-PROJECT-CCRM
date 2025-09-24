package edu.ccrm.domain;

import java.time.LocalDateTime;

/**
 * Course class with Builder pattern implementation
 * Demonstrates Builder design pattern and immutability concepts
 */
public class Course {
    private final String code;
    private final String title;
    private final int credits;
    private final String instructor;
    private final Semester semester;
    private final String department;
    private final LocalDateTime createdAt;
    private boolean active;
    
    // Private constructor for Builder pattern
    private Course(Builder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.instructor = builder.instructor;
        this.semester = builder.semester;
        this.department = builder.department;
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }
    
    // Getters
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getInstructor() { return instructor; }
    public Semester getSemester() { return semester; }
    public String getDepartment() { return department; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    @Override
    public String toString() {
        return String.format("Course{code='%s', title='%s', credits=%d, instructor='%s', semester=%s}", 
                           code, title, credits, instructor, semester);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return code.equals(course.code);
    }
    
    @Override
    public int hashCode() {
        return code.hashCode();
    }
    
    // Builder Pattern Implementation
    public static class Builder {
        private String code;
        private String title;
        private int credits;
        private String instructor;
        private Semester semester;
        private String department;
        
        public Builder setCode(String code) {
            this.code = code;
            return this;
        }
        
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }
        
        public Builder setCredits(int credits) {
            this.credits = credits;
            return this;
        }
        
        public Builder setInstructor(String instructor) {
            this.instructor = instructor;
            return this;
        }
        
        public Builder setSemester(Semester semester) {
            this.semester = semester;
            return this;
        }
        
        public Builder setDepartment(String department) {
            this.department = department;
            return this;
        }
        
        public Course build() {
            // Validation
            if (code == null || title == null || credits <= 0 || semester == null) {
                throw new IllegalArgumentException("Required course fields cannot be null or invalid");
            }
            return new Course(this);
        }
    }
}