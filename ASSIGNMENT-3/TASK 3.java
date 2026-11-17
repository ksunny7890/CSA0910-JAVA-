import java.io.*;
import java.util.*;

public class Q3_ResourceSafetyReport {

    // Creates sample store files so the program can run immediately.
    static void createSampleFiles() throws IOException {
        String[] names = {"store1.txt", "store2.txt", "store3.txt"};
        String[] contents = {"1000", "1500", "2000"};
        for (int i = 0; i < names.length; i++) {
            try (FileWriter writer = new FileWriter(names[i])) {
                writer.write(contents[i]);
            }
        }
    }

    // Safer implementation: try-with-resources closes each file automatically.
    static int generateWithTryResources(List<String> files) {
        int total = 0;
        for (String name : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(name))) {
                total += Integer.parseInt(reader.readLine().trim());
                System.out.println("Read successfully: " + name);
            } catch (IOException | NumberFormatException e) {
                System.out.println("Failed to process " + name + ": " + e.getMessage());
            }
        }
        return total;
    }

    // Manual close() implementation. The finally block attempts to close the reader.
    static int generateWithManualClose(List<String> files) {
        int total = 0;
        for (String name : files) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(name));
                total += Integer.parseInt(reader.readLine().trim());
                System.out.println("Read successfully: " + name);
            } catch (IOException | NumberFormatException e) {
                System.out.println("Failed to process " + name + ": " + e.getMessage());
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        System.out.println("Could not close " + name + ": " + e.getMessage());
                    }
                }
            }
        }
        return total;
    }

    public static void main(String[] args) throws IOException {
        createSampleFiles();
        List<String> inputFiles =
            Arrays.asList("store1.txt", "store2.txt", "missing-store.txt", "store3.txt");

        System.out.println("=== Manual close() version ===");
        int manualTotal = generateWithManualClose(inputFiles);
        System.out.println("Manual report total: " + manualTotal);

        System.out.println("\n=== try-with-resources version ===");
        int automaticTotal = generateWithTryResources(inputFiles);
        System.out.println("Try-with-resources report total: " + automaticTotal);

        System.out.println("\nBoth versions continue after a bad file and close opened readers.");
        System.out.println("The expected total is 4500.");
    }
}
