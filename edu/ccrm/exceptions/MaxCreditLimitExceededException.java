package edu.ccrm.exceptions;

/**
 * Custom unchecked exception for credit limit violations
 * Demonstrates runtime exception creation
 */
public class MaxCreditLimitExceededException extends RuntimeException {
    private final String studentId;
    private final int currentCredits;
    private final int maxCredits;
    
    public MaxCreditLimitExceededException(String studentId, int currentCredits, int maxCredits) {
        super(String.format("Student %s has %d credits, exceeding maximum limit of %d", 
              studentId, currentCredits, maxCredits));
        this.studentId = studentId;
        this.currentCredits = currentCredits;
        this.maxCredits = maxCredits;
    }
    
    public MaxCreditLimitExceededException(String studentId, int currentCredits, int maxCredits, String message) {
        super(message);
        this.studentId = studentId;
        this.currentCredits = currentCredits;
        this.maxCredits = maxCredits;
    }
    
    public String getStudentId() { return studentId; }
    public int getCurrentCredits() { return currentCredits; }
    public int getMaxCredits() { return maxCredits; }
}