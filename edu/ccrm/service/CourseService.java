package edu.ccrm.service;

import edu.ccrm.domain.*;
import edu.ccrm.util.Validators;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Course Service implementing business logic
 * Demonstrates Stream API usage and functional programming
 */
public class CourseService implements DataService<Course> {
    private final Map<String, Course> courses;
    
    public CourseService() {
        this.courses = new HashMap<>();
    }
    
    // CRUD Operations
    public void addCourse(Course course) {
        validateData(course);
        courses.put(course.getCode(), course);
    }
    
    public void updateCourse(Course course) {
        if (!exists(course.getCode())) {
            throw new IllegalArgumentException("Course not found: " + course.getCode());
        }
        validateData(course);
        courses.put(course.getCode(), course);
    }
    
    public void deactivateCourse(String courseCode) {
        Course course = findById(courseCode);
        if (course != null) {
            course.setActive(false);
        }
    }
    
    // Search and Filter operations using Stream API
    public List<Course> findByInstructor(String instructor) {
        return findBy(course -> instructor.equals(course.getInstructor()));
    }
    
    public List<Course> findByDepartment(String department) {
        return findBy(course -> department.equals(course.getDepartment()));
    }
    
    public List<Course> findBySemester(Semester semester) {
        return findBy(course -> semester.equals(course.getSemester()));
    }
    
    public List<Course> findByCredits(int minCredits, int maxCredits) {
        return findBy(course -> course.getCredits() >= minCredits && course.getCredits() <= maxCredits);
    }
    
    // Advanced search with multiple criteria
    public List<Course> searchCourses(String instructor, String department, Semester semester) {
        return courses.values().stream()
                .filter(course -> instructor == null || instructor.equals(course.getInstructor()))
                .filter(course -> department == null || department.equals(course.getDepartment()))
                .filter(course -> semester == null || semester.equals(course.getSemester()))
                .filter(Course::isActive)
                .sorted(Comparator.comparing(Course::getCode))
                .collect(Collectors.toList());
    }
    
    // Interface implementations
    @Override
    public String getId() {
        return "CourseService";
    }
    
    @Override
    public void save() {
        System.out.println("Saving " + courses.size() + " courses...");
    }
    
    @Override
    public void delete() {
        courses.clear();
    }
    
    @Override
    public List<Course> findAll() {
        return new ArrayList<>(courses.values());
    }
    
    @Override
    public List<Course> findBy(Predicate<Course> criteria) {
        return courses.values().stream()
                .filter(criteria)
                .collect(Collectors.toList());
    }
    
    @Override
    public Course findById(String code) {
        return courses.get(code);
    }
    
    @Override
    public void validateData(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (course.getCredits() < 1 || course.getCredits() > 6) {
            throw new IllegalArgumentException("Course credits must be between 1 and 6");
        }
        // Additional validations...
    }
    
    @Override
    public void backup() {
        System.out.println("Backing up course data...");
    }
    
    @Override
    public void restore() {
        System.out.println("Restoring course data...");
    }
    
    // Utility methods with Stream API
    public Map<String, Long> getCoursesByDepartment() {
        return courses.values().stream()
                .filter(Course::isActive)
                .collect(Collectors.groupingBy(
                    Course::getDepartment,
                    Collectors.counting()
                ));
    }
    
    public Map<Semester, List<Course>> getCoursesBySemester() {
        return courses.values().stream()
                .filter(Course::isActive)
                .collect(Collectors.groupingBy(Course::getSemester));
    }
    
    public double getAverageCredits() {
        return courses.values().stream()
                .filter(Course::isActive)
                .mapToInt(Course::getCredits)
                .average()
                .orElse(0.0);
    }
}