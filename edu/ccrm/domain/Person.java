package edu.ccrm.domain;

import java.time.LocalDateTime;

/**
 * Abstract base class for all persons in the system
 * Demonstrates inheritance, abstraction, and encapsulation
 */
public abstract class Person {
    protected final String id;
    protected String fullName;
    protected String email;
    protected LocalDateTime createdAt;
    protected boolean active;
    
    public Person(String id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract String getRole();
    
    // Encapsulation - getters and setters
    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    @Override
    public String toString() {
        return String.format("Person{id='%s', name='%s', email='%s', role='%s'}", 
                           id, fullName, email, getRole());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return id.equals(person.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}