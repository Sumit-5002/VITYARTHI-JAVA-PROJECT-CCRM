package edu.ccrm.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Application configuration using Singleton pattern
 * Demonstrates Singleton design pattern and configuration management
 */
public class AppConfig {
    private static volatile AppConfig instance;
    private static final Object lock = new Object();
    
    // Configuration properties
    private final String dataFolder;
    private final String backupFolder;
    private final String exportFolder;
    private final int maxCreditsPerSemester;
    private final DateTimeFormatter backupDateFormat;
    
    // Private constructor for Singleton
    private AppConfig() {
        this.dataFolder = "data";
        this.backupFolder = "backups";
        this.exportFolder = "exports";
        this.maxCreditsPerSemester = 24;
        this.backupDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        
        initializeFolders();
    }
    
    /**
     * Thread-safe Singleton implementation with double-checked locking
     */
    public static AppConfig getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new AppConfig();
                }
            }
        }
        return instance;
    }
    
    /**
     * Initialize required folders
     */
    private void initializeFolders() {
        try {
            Files.createDirectories(Paths.get(dataFolder));
            Files.createDirectories(Paths.get(backupFolder));
            Files.createDirectories(Paths.get(exportFolder));
            Files.createDirectories(Paths.get("test-data"));
        } catch (IOException e) {
            System.err.println("Failed to create directories: " + e.getMessage());
        }
    }
    
    // Getters
    public String getDataFolder() { return dataFolder; }
    public String getBackupFolder() { return backupFolder; }
    public String getExportFolder() { return exportFolder; }
    public int getMaxCreditsPerSemester() { return maxCreditsPerSemester; }
    
    /**
     * Generate timestamped backup folder name
     */
    public String generateBackupFolderName() {
        String timestamp = LocalDateTime.now().format(backupDateFormat);
        return backupFolder + "/backup_" + timestamp;
    }
    
    /**
     * Get application information
     */
    public String getAppInfo() {
        return """
                Campus Course & Records Manager (CCRM)
                Version: 1.0
                Java Platform: Java SE (Standard Edition)
                Architecture: Console-based application
                Features: Student/Course management, Grading, File I/O, Backup
                """;
    }
    
    /**
     * Get Java platform comparison
     */
    public String getJavaPlatformComparison() {
        return """
                Java Platform Editions:
                • Java ME (Micro Edition): For mobile devices and embedded systems
                • Java SE (Standard Edition): For desktop and server applications (Current)
                • Java EE (Enterprise Edition): For large-scale enterprise applications
                """;
    }
    
    // Prevent cloning
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Cannot clone singleton instance");
    }
    
    // Handle serialization (if needed)
    private Object readResolve() {
        return getInstance();
    }
}