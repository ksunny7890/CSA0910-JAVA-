class ParkingTicket {
    private int ticketNo;
    private String vehicleNo;

    // Default constructor uses constructor chaining.
    ParkingTicket() {
        this(0, "Unknown");
    }

    // Parameterized constructor.
    ParkingTicket(int ticketNo, String vehicleNo) {
        this.ticketNo = ticketNo;
        this.vehicleNo = vehicleNo;
    }

    // Copy constructor.
    ParkingTicket(ParkingTicket other) {
        this(other.ticketNo, other.vehicleNo);
    }

    void display() {
        System.out.println("Ticket: " + ticketNo);
        System.out.println("Vehicle: " + vehicleNo);
    }
}

public class Task4 {
    public static void main(String[] args) {
        ParkingTicket first = new ParkingTicket(101, "TN01AB1111");
        ParkingTicket copy = new ParkingTicket(first);

        first.display();
        copy.display();
    }
}
