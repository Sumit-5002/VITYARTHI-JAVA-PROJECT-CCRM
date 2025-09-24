package edu.ccrm.service;

import java.util.List;
import java.util.function.Predicate;

/**
 * Generic interface for searchable entities
 * Demonstrates generic interfaces and functional programming
 */
public interface Searchable<T> {
    List<T> findAll();
    List<T> findBy(Predicate<T> criteria);
    T findById(String id);
    
    // Default method with diamond problem potential
    default boolean exists(String id) {
        return findById(id) != null;
    }
    
    // Another default method for demonstration
    default long count() {
        return findAll().size();
    }
}