package edu.ccrm.domain;

/**
 * Semester enum with constructors and fields
 * Demonstrates enum with constructor and methods
 */
public enum Semester {
    SPRING("Spring", 1),
    SUMMER("Summer", 2), 
    FALL("Fall", 3),
    WINTER("Winter", 4);
    
    private final String displayName;
    private final int order;
    
    Semester(String displayName, int order) {
        this.displayName = displayName;
        this.order = order;
    }
    
    public String getDisplayName() { return displayName; }
    public int getOrder() { return order; }
    
    @Override
    public String toString() {
        return displayName;
    }
    
    public static Semester fromString(String text) {
        for (Semester s : Semester.values()) {
            if (s.displayName.equalsIgnoreCase(text) || s.name().equalsIgnoreCase(text)) {
                return s;
            }
        }
        throw new IllegalArgumentException("No semester found for: " + text);
    }
}