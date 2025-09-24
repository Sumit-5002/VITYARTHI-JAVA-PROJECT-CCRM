package edu.ccrm.service;

/**
 * Interface for objects that can be persisted
 * Demonstrates interface design
 */
public interface Persistable<T> {
    String getId();
    void save();
    void delete();
    
    // Default method to show diamond problem resolution
    default boolean isValid() {
        return getId() != null && !getId().trim().isEmpty();
    }
}