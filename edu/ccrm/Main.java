package edu.ccrm;

import edu.ccrm.cli.MenuSystem;
import edu.ccrm.config.AppConfig;

/**
 * Main class for Campus Course & Records Manager (CCRM)
 * Demonstrates Java application entry point and configuration
 */
public class Main {
    
    /**
     * Main method - application entry point
     * Demonstrates basic Java syntax, exception handling, and assertions
     */
    public static void main(String[] args) {
        // Enable assertions example
        assert args != null : "Arguments array cannot be null";
        
        try {
            // Display application startup information
            System.out.println("Starting Campus Course & Records Manager...");
            
            // Initialize configuration (Singleton pattern)
            AppConfig config = AppConfig.getInstance();
            System.out.println("Configuration loaded successfully.");
            
            // Demonstrate primitive variables and operators
            int currentYear = 2025;
            boolean isProduction = false;
            double version = 1.0;
            
            // Operator precedence example (documented as required)
            int result = 10 + 5 * 2; // Result: 20 (multiplication before addition)
            System.out.println("Operator precedence example: 10 + 5 * 2 = " + result);
            
            // Bitwise operators example
            int flags = 0b1010; // Binary literal
            int mask = 0b1100;
            int bitwiseAnd = flags & mask; // Result: 0b1000 (8)
            int bitwiseOr = flags | mask;  // Result: 0b1110 (14)
            System.out.println("Bitwise AND: " + bitwiseAnd + ", Bitwise OR: " + bitwiseOr);
            
            // Array operations with Arrays utility class
            String[] courseTypes = {"Core", "Elective", "Lab", "Project"};
            java.util.Arrays.sort(courseTypes); // Sorting using Arrays utility
            System.out.println("Sorted course types: " + java.util.Arrays.toString(courseTypes));
            
            // String operations demonstration
            String appName = "Campus Course & Records Manager";
            String[] nameParts = appName.split(" "); // String split
            String acronym = java.util.Arrays.stream(nameParts)
                .map(part -> part.substring(0, 1))
                .reduce("", String::concat);
            System.out.println("Application acronym: " + acronym);
            
            // Decision structure examples
            if (args.length > 0) {
                System.out.println("Command line arguments provided: " + java.util.Arrays.toString(args));
                
                // Nested if-else example
                if (args[0].equals("--version")) {
                    System.out.println("CCRM Version " + version);
                    return;
                } else if (args[0].equals("--help")) {
                    displayHelp();
                    return;
                }
            } else {
                System.out.println("No command line arguments provided. Starting interactive mode.");
            }
            
            // Loop examples with jump controls
            System.out.println("\\nDemonstrating loop constructs:");
            
            // while loop with break
            int counter = 0;
            while (true) {
                counter++;
                if (counter > 3) break; // Jump control: break
                System.out.println("While loop iteration: " + counter);
            }
            
            // do-while loop with continue
            counter = 0;
            do {
                counter++;
                if (counter == 2) continue; // Jump control: continue
                System.out.println("Do-while loop iteration: " + counter);
            } while (counter < 3);
            
            // for loop
            for (int i = 1; i <= 3; i++) {
                System.out.println("For loop iteration: " + i);
            }
            
            // enhanced for loop
            for (String courseType : courseTypes) {
                System.out.println("Course type: " + courseType);
            }
            
            // Labeled break example (rare but required)
            outer: for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (i == 1 && j == 1) {
                        System.out.println("Breaking out of nested loops at i=" + i + ", j=" + j);
                        break outer; // Labeled jump
                    }
                }
            }
            
            System.out.println("\\nInitializing application components...");
            
            // Start the menu system
            MenuSystem menuSystem = new MenuSystem();
            menuSystem.start();
            
        } catch (Exception e) {
            System.err.println("Fatal error starting application: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } finally {
            System.out.println("Application shutdown completed.");
        }
    }
    
    /**
     * Display help information
     * Demonstrates method definition and string operations
     */
    private static void displayHelp() {
        String helpText = """
                Campus Course & Records Manager (CCRM) - Help
                
                Usage: java edu.ccrm.Main [options]
                
                Options:
                  --version    Display version information
                  --help       Display this help message
                  
                Interactive Commands:
                  1. Student Management - Add, update, list students
                  2. Course Management - Add, update, list courses  
                  3. Enrollment Management - Enroll/unenroll students
                  4. Grade Management - Assign grades, calculate GPA
                  5. Import/Export - CSV file operations
                  6. Backup Operations - Backup and restore data
                  7. Reports - Generate analytics and reports
                  
                Examples:
                  java edu.ccrm.Main                 # Start interactive mode
                  java edu.ccrm.Main --version       # Show version
                  java -ea edu.ccrm.Main            # Enable assertions
                  
                For more information, visit: https://github.com/yourname/ccrm
                """;
        
        System.out.println(helpText);
    }
    
    /**
     * Demonstrate type casting and instanceof checks
     * (Called from menu system when needed)
     */
    public static void demonstrateTypeCasting() {
        // Upcasting (implicit)
        Object obj = "Hello World"; // String -> Object (upcasting)
        
        // Downcasting with instanceof check (explicit and safe)
        if (obj instanceof String str) { // Pattern matching (Java 16+)
            System.out.println("String length: " + str.length());
        } else if (obj instanceof Integer num) {
            System.out.println("Integer value: " + num);
        }
        
        // Traditional instanceof with explicit cast
        if (obj instanceof String) {
            String str = (String) obj; // Explicit downcast
            System.out.println("Traditional cast - String: " + str.toUpperCase());
        }
    }
}