package edu.ccrm.util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicLong;

/**
 * File utility class demonstrating recursion and NIO.2
 * Demonstrates recursive algorithms and modern Java I/O
 */
public final class FileUtils {
    
    private FileUtils() {
        throw new AssertionError("Utility class cannot be instantiated");
    }
    
    /**
     * Recursively calculate total size of directory
     * Demonstrates recursion and NIO.2 file walking
     */
    public static long calculateDirectorySize(Path directory) throws IOException {
        final AtomicLong size = new AtomicLong(0);
        
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                size.addAndGet(attrs.size());
                return FileVisitResult.CONTINUE;
            }
            
            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                System.err.println("Failed to visit file: " + file + " (" + exc.getMessage() + ")");
                return FileVisitResult.CONTINUE;
            }
        });
        
        return size.get();
    }
    
    /**
     * Recursively list all files in directory with depth information
     * Demonstrates recursive file traversal
     */
    public static void listFilesRecursively(Path directory, int maxDepth) {
        listFilesRecursively(directory, 0, maxDepth);
    }
    
    private static void listFilesRecursively(Path directory, int currentDepth, int maxDepth) {
        if (currentDepth > maxDepth) return;
        
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path entry : stream) {
                // Print with indentation based on depth
                String indent = "  ".repeat(currentDepth);
                if (Files.isDirectory(entry)) {
                    System.out.println(indent + "[DIR]  " + entry.getFileName());
                    // Recursive call for subdirectories
                    listFilesRecursively(entry, currentDepth + 1, maxDepth);
                } else {
                    System.out.println(indent + "[FILE] " + entry.getFileName() + 
                                     " (" + formatFileSize(Files.size(entry)) + ")");
                }
            }
        } catch (IOException e) {
            System.err.println("Error listing directory: " + directory + " (" + e.getMessage() + ")");
        }
    }
    
    /**
     * Format file size in human-readable format
     */
    public static String formatFileSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp - 1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }
    
    /**
     * Copy directory recursively
     */
    public static void copyDirectory(Path source, Path target) throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path targetDir = target.resolve(source.relativize(dir));
                Files.createDirectories(targetDir);
                return FileVisitResult.CONTINUE;
            }
            
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path targetFile = target.resolve(source.relativize(file));
                Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }
    
    /**
     * Backup files to timestamped directory
     */
    public static void createBackup(Path sourceDir, Path backupBaseDir, String timestamp) throws IOException {
        Path backupDir = backupBaseDir.resolve("backup_" + timestamp);
        Files.createDirectories(backupDir);
        copyDirectory(sourceDir, backupDir);
        System.out.println("Backup created: " + backupDir);
    }
}