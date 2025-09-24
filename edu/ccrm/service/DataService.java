package edu.ccrm.service;

/**
 * Combined interface showing diamond problem resolution
 * Demonstrates interface inheritance and default method conflicts
 */
public interface DataService<T> extends Persistable<T>, Searchable<T> {
    
    // Explicit override to resolve diamond problem
    @Override
    default boolean isValid() {
        // Custom implementation combining both interfaces
        return Persistable.super.isValid() && count() >= 0;
    }
    
    // Additional methods specific to data services
    void validateData(T entity);
    void backup();
    void restore();
}