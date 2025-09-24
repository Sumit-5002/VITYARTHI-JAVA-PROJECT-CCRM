package edu.ccrm.domain;

import java.util.Objects;

/**
 * Immutable value object for Course Codes
 * Demonstrates immutability with final fields and defensive copying
 */
public final class CourseCode {
    private final String department;
    private final int number;
    private final String section;
    
    public CourseCode(String department, int number, String section) {
        // Defensive copying and validation
        this.department = Objects.requireNonNull(department, "Department cannot be null").toUpperCase();
        this.number = number;
        this.section = Objects.requireNonNull(section, "Section cannot be null").toUpperCase();
        
        // Validation
        if (department.length() < 2 || department.length() > 4) {
            throw new IllegalArgumentException("Department code must be 2-4 characters");
        }
        if (number < 100 || number > 999) {
            throw new IllegalArgumentException("Course number must be 100-999");
        }
    }
    
    // No setters - immutable
    public String getDepartment() { return department; }
    public int getNumber() { return number; }
    public String getSection() { return section; }
    
    public String getFullCode() {
        return String.format("%s%d-%s", department, number, section);
    }
    
    @Override
    public String toString() {
        return getFullCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CourseCode that = (CourseCode) obj;
        return number == that.number &&
               Objects.equals(department, that.department) &&
               Objects.equals(section, that.section);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(department, number, section);
    }
    
    public static CourseCode parse(String code) {
        // Parse format: "CS101-A"
        String[] parts = code.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid course code format: " + code);
        }
        
        String deptPart = parts[0];
        String section = parts[1];
        
        // Extract department and number
        int i = 0;
        while (i < deptPart.length() && Character.isLetter(deptPart.charAt(i))) {
            i++;
        }
        
        String dept = deptPart.substring(0, i);
        int number = Integer.parseInt(deptPart.substring(i));
        
        return new CourseCode(dept, number, section);
    }
}