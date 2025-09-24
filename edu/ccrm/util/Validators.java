package edu.ccrm.util;

import java.util.regex.Pattern;

/**
 * Utility class for validation operations
 * Demonstrates static utility methods and regex usage
 */
public final class Validators {
    
    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    // Private constructor to prevent instantiation
    private Validators() {
        throw new AssertionError("Utility class cannot be instantiated");
    }
    
    /**
     * Validates email format using regex
     * @param email the email to validate
     * @return true if valid email format
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Validates student registration number format
     * Expected format: YYYY-DEPT-NNNN (e.g., 2023-CS-0001)
     */
    public static boolean isValidRegNo(String regNo) {
        if (regNo == null || regNo.length() != 13) return false;
        
        String[] parts = regNo.split("-");
        if (parts.length != 3) return false;
        
        try {
            int year = Integer.parseInt(parts[0]);
            if (year < 2000 || year > 2030) return false;
            
            String dept = parts[1];
            if (dept.length() < 2 || dept.length() > 4) return false;
            
            int number = Integer.parseInt(parts[2]);
            if (number < 1 || number > 9999) return false;
            
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validates course code format
     * Expected format: DEPTXXX-S (e.g., CS101-A)
     */
    public static boolean isValidCourseCode(String courseCode) {
        if (courseCode == null || courseCode.length() < 6) return false;
        
        try {
            String[] parts = courseCode.split("-");
            if (parts.length != 2) return false;
            
            String deptNum = parts[0];
            String section = parts[1];
            
            // Extract department and number
            int i = 0;
            while (i < deptNum.length() && Character.isLetter(deptNum.charAt(i))) {
                i++;
            }
            
            if (i == 0 || i >= deptNum.length()) return false;
            
            String dept = deptNum.substring(0, i);
            int number = Integer.parseInt(deptNum.substring(i));
            
            return dept.length() >= 2 && dept.length() <= 4 && 
                   number >= 100 && number <= 999 &&
                   section.length() == 1;
                   
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validates credit range
     */
    public static boolean isValidCredits(int credits) {
        return credits >= 1 && credits <= 6;
    }
    
    /**
     * Validates string is not null or empty
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
    
    /**
     * Validates ID format (alphanumeric, 5-20 characters)
     */
    public static boolean isValidId(String id) {
        if (id == null || id.length() < 5 || id.length() > 20) return false;
        return id.matches("^[a-zA-Z0-9]+$");
    }
}