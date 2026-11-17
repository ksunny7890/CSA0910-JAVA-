import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Q5_CinemaSeatBooking {
    private final boolean[] booked;
    private final AtomicInteger bookingsMade = new AtomicInteger(0);

    Q5_CinemaSeatBooking(int seats) {
        booked = new boolean[seats];
    }

    // synchronized makes checking and booking one atomic operation.
    public synchronized void book(int seatNumber) {
        if (seatNumber < 0 || seatNumber >= booked.length) {
            System.out.println("Invalid seat: " + seatNumber);
            return;
        }

        if (!booked[seatNumber]) {
            booked[seatNumber] = true;
            bookingsMade.incrementAndGet();
            System.out.println(Thread.currentThread().getName()
                               + " booked seat " + (seatNumber + 1));
        } else {
            System.out.println(Thread.currentThread().getName()
                               + " could not book seat " + (seatNumber + 1)
                               + " (already booked)");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Q5_CinemaSeatBooking screen = new Q5_CinemaSeatBooking(10);
        Thread[] users = new Thread[100];
        CountDownLatch startSignal = new CountDownLatch(1);

        for (int i = 0; i < users.length; i++) {
            final int seat = i % 10;
            users[i] = new Thread(() -> {
                try {
                    startSignal.await();
                    screen.book(seat);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "User-" + (i + 1));
            users[i].start();
        }

        startSignal.countDown(); // Let all users compete at nearly the same time.
        for (Thread user : users) {
            user.join();
        }

        System.out.println("\nSeats sold: " + screen.bookingsMade.get());
        System.out.println("Expected seats sold: 10");
    }
}
