import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Q7_ConcurrentResultProcessor {

    static class Result {
        final String fileName;
        final int score;
        Result(String fileName, int score) {
            this.fileName = fileName;
            this.score = score;
        }
    }

    static class ScoreTask implements Callable<Result> {
        private final Path file;

        ScoreTask(Path file) { this.file = file; }

        @Override
        public Result call() throws Exception {
            String text = Files.readString(file).trim();
            int score = Integer.parseInt(text);

            if (score < 0 || score > 100) {
                throw new IllegalArgumentException("Score must be between 0 and 100");
            }
            return new Result(file.getFileName().toString(), score);
        }
    }

    static void createSampleFiles(Path folder) throws IOException {
        Files.createDirectories(folder);
        String[][] samples = {
            {"student1.txt", "85"},
            {"student2.txt", "92"},
            {"student3.txt", "bad-score"},
            {"student4.txt", "76"},
            {"student5.txt", "110"}
        };
        for (String[] sample : samples) {
            Files.writeString(folder.resolve(sample[0]), sample[1]);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Path folder = Paths.get("scores");
        createSampleFiles(folder);

        List<Path> scoreFiles = new ArrayList<>();
        try (Stream<Path> paths = Files.list(folder)) {
            paths.filter(p -> p.toString().endsWith(".txt"))
                 .sorted()
                 .forEach(scoreFiles::add);
        }

        int poolSize = 4; // Try 1, 2, 4 and 8 to compare execution time.
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        CompletionService<Result> completion =
            new ExecutorCompletionService<>(executor);

        ConcurrentMap<String, Integer> successfulResults =
            new ConcurrentHashMap<>();
        AtomicInteger failures = new AtomicInteger(0);

        long start = System.nanoTime();

        for (Path file : scoreFiles) {
            completion.submit(new ScoreTask(file));
        }

        for (int i = 0; i < scoreFiles.size(); i++) {
            try {
                Result result = completion.take().get();
                successfulResults.put(result.fileName, result.score);
                System.out.println("SUCCESS: " + result.fileName +
                                   " -> " + result.score);
            } catch (ExecutionException e) {
                failures.incrementAndGet();
                System.out.println("FAILED: " + e.getCause().getMessage());
            }
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        long elapsedMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
        int total = successfulResults.values().stream()
                                     .mapToInt(Integer::intValue).sum();

        System.out.println("\n===== PROCESSING SUMMARY =====");
        System.out.println("Files found: " + scoreFiles.size());
        System.out.println("Successful files: " + successfulResults.size());
        System.out.println("Failed files: " + failures.get());
        System.out.println("Total of valid scores: " + total);
        System.out.println("Thread pool size: " + poolSize);
        System.out.println("Processing time: " + elapsedMs + " ms");
    }
}
