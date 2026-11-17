import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Q1_ATMExceptionHandling {
    static class InsufficientBalanceException extends Exception {
        InsufficientBalanceException(String message) { super(message); }
    }

    static void withdraw(double balance, double amount)
            throws InsufficientBalanceException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient account balance.");
        }
        System.out.printf("Please collect your cash: Rs. %.2f%n", amount);
        System.out.printf("Remaining balance: Rs. %.2f%n", balance - amount);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("===== ATM WITHDRAWAL =====");
        try {
            System.out.print("Enter account number: ");
            String account = sc.nextLine().trim();
            if (account.isEmpty()) {
                throw new IllegalArgumentException("Account number cannot be empty.");
            }

            System.out.print("Enter withdrawal amount: ");
            double amount = sc.nextDouble();

            // Simulate checking the card-reader file.
            File cardReader = new File("card-reader.txt");
            if (!cardReader.exists()) {
                throw new FileNotFoundException(
                    "Card-reader file is missing: " + cardReader.getName());
            }

            double balance = 10000.00;
            System.out.println("Account verified: " + account);
            withdraw(balance, amount);

        } catch (InputMismatchException e) {
            System.out.println("Invalid input: enter the amount using numbers only.");
        } catch (InsufficientBalanceException e) {
            System.out.println("Transaction declined: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Device error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid details: " + e.getMessage());
        } finally {
            System.out.println("ATM session closed. Thank you.");
            sc.close();
        }
    }
}
