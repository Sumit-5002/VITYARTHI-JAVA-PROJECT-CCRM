package edu.ccrm.domain;

import java.util.*;

/**
 * Instructor class extending Person
 * Demonstrates inheritance and polymorphism
 */
public class Instructor extends Person {
    private final String employeeId;
    private String department;
    private final Set<String> assignedCourses;
    
    public Instructor(String id, String employeeId, String fullName, String email, String department) {
        super(id, fullName, email);
        this.employeeId = employeeId;
        this.department = department;
        this.assignedCourses = new HashSet<>();
    }
    
    @Override
    public String getRole() {
        return "INSTRUCTOR";
    }
    
    public String getEmployeeId() { return employeeId; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public Set<String> getAssignedCourses() { return new HashSet<>(assignedCourses); }
    
    public void assignCourse(String courseCode) {
        assignedCourses.add(courseCode);
    }
    
    public void unassignCourse(String courseCode) {
        assignedCourses.remove(courseCode);
    }
    
    @Override
    public String toString() {
        return String.format("Instructor{empId='%s', name='%s', dept='%s', courses=%d}", 
                           employeeId, fullName, department, assignedCourses.size());
    }
}