import java.io.IOException;

class ExamException extends Exception {
    ExamException(String message) { super(message); }
    ExamException(String message, Throwable cause) { super(message, cause); }
}

class ExamWindowClosedException extends ExamException {
    ExamWindowClosedException(String message) { super(message); }
}

class StudentNotRegisteredException extends ExamException {
    StudentNotRegisteredException(String message) { super(message); }
}

class SubmissionStorageException extends ExamException {
    SubmissionStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

class Storage {
    void save(String fileName, long sizeKb) throws SubmissionStorageException {
        try {
            if (sizeKb > 500) {
                throw new IOException("Disk quota exceeded");
            }
            System.out.println("Saved " + fileName);
        } catch (IOException e) {
            // Preserve the original IOException as the cause.
            throw new SubmissionStorageException(
                "Could not store " + fileName, e);
        }
    }
}

class ExamService {
    private final Storage storage = new Storage();

    void submit(String studentId, boolean registered, boolean examOpen,
                String fileName, long sizeKb) throws ExamException {
        if (!registered) {
            throw new StudentNotRegisteredException(
                "Student " + studentId + " is not registered for this course.");
        }
        if (!examOpen) {
            throw new ExamWindowClosedException("The examination window is closed.");
        }
        storage.save(fileName, sizeKb);
    }
}

public class Q2_ExamPortalCustomExceptions {
    public static void main(String[] args) {
        ExamService service = new ExamService();

        // First submission succeeds; second demonstrates exception chaining.
        try {
            service.submit("STU101", true, true, "answer01.pdf", 120);
            service.submit("STU102", true, true, "answer02.pdf", 800);
        } catch (ExamException e) {
            System.out.println("Error: " + e.getMessage());
            if (e.getCause() != null) {
                System.out.println("Cause: " + e.getCause().getMessage());
            }
            System.out.println("\nFull exception details:");
            e.printStackTrace(System.out);
        }

        // Uncomment to test other custom exceptions:
        // service.submit("STU103", false, true, "answer03.pdf", 100);
        // service.submit("STU104", true, false, "answer04.pdf", 100);
    }
}
