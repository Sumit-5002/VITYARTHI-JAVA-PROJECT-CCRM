package edu.ccrm.domain;

public enum Grade {
    S(10.0, "Outstanding"),
    A(9.0, "Excellent"), 
    B(8.0, "Very Good"),
    C(7.0, "Good"),
    D(6.0, "Average"),
    E(5.0, "Below Average"),
    F(0.0, "Fail");
    
    private final double gradePoints;
    private final String description;
    
    Grade(double gradePoints, String description) {
        this.gradePoints = gradePoints;
        this.description = description;
    }
    
    public double getGradePoints() { return gradePoints; }
    public String getDescription() { return description; }
    
    public boolean isPassing() {
        return this != F;
    }
    
    @Override
    public String toString() {
        return String.format("%s (%.1f) - %s", name(), gradePoints, description);
    }
    
    public static Grade fromGradePoints(double points) {
        for (Grade grade : Grade.values()) {
            if (Math.abs(grade.gradePoints - points) < 0.1) {
                return grade;
            }
        }
        throw new IllegalArgumentException("No grade found for points: " + points);
    }
}