public class Q6_DeadlockAndTokenCounter {

    static class Wallet {
        private final int id;
        private int balance;

        Wallet(int id, int balance) {
            this.id = id;
            this.balance = balance;
        }

        int getId() { return id; }
        int getBalance() { return balance; }
        void debit(int amount) { balance -= amount; }
        void credit(int amount) { balance += amount; }
    }

    // Consistent lock ordering prevents circular wait and therefore deadlock.
    static void transfer(Wallet from, Wallet to, int amount) {
        Wallet first = from.getId() < to.getId() ? from : to;
        Wallet second = from.getId() < to.getId() ? to : from;

        synchronized (first) {
            synchronized (second) {
                if (amount <= 0 || from.getBalance() < amount) {
                    System.out.println("Transfer rejected.");
                    return;
                }
                from.debit(amount);
                to.credit(amount);
                System.out.println("Transferred Rs. " + amount +
                                   " from wallet " + from.getId() +
                                   " to wallet " + to.getId());
            }
        }
    }

    static class TokenCounter {
        private final int[] buffer = new int[5];
        private int count = 0, in = 0, out = 0;

        public synchronized void put(int token) throws InterruptedException {
            while (count == buffer.length) {
                wait();
            }
            buffer[in] = token;
            in = (in + 1) % buffer.length;
            count++;
            notifyAll();
        }

        public synchronized int take() throws InterruptedException {
            while (count == 0) {
                wait();
            }
            int token = buffer[out];
            out = (out + 1) % buffer.length;
            count--;
            notifyAll();
            return token;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Wallet wallet1 = new Wallet(1, 1000);
        Wallet wallet2 = new Wallet(2, 1000);

        Thread transferA = new Thread(() -> transfer(wallet1, wallet2, 100));
        Thread transferB = new Thread(() -> transfer(wallet2, wallet1, 200));
        transferA.start();
        transferB.start();
        transferA.join();
        transferB.join();

        System.out.println("Wallet 1 balance: " + wallet1.getBalance());
        System.out.println("Wallet 2 balance: " + wallet2.getBalance());

        TokenCounter counter = new TokenCounter();
        int[] collected = new int[2];

        Thread cook = new Thread(() -> {
            try {
                for (int token = 1; token <= 20; token++) {
                    counter.put(token);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Cook");

        Thread[] students = new Thread[2];
        for (int i = 0; i < students.length; i++) {
            final int id = i;
            students[i] = new Thread(() -> {
                try {
                    for (int k = 0; k < 10; k++) {
                        collected[id] += counter.take();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Student-" + (i + 1));
        }

        cook.start();
        for (Thread student : students) student.start();
        cook.join();
        for (Thread student : students) student.join();

        System.out.println("Sum of tokens collected: " +
            (collected[0] + collected[1]) + " (expected 210)");
    }
}
