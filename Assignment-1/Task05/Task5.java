class ParkingTicket {
    // Shared by all ticket objects.
    private static int count = 0;

    private final int ticketNo;
    private final String vehicleNo;

    ParkingTicket(String vehicleNo) {
        this.vehicleNo = vehicleNo;
        count++;
        ticketNo = count;
    }

    void display() {
        System.out.println(ticketNo + " " + vehicleNo);
    }

    static int getCount() {
        return count;
    }
}

public class Task5 {
    public static void main(String[] args) {
        ParkingTicket first = new ParkingTicket("TN01AB1111");
        ParkingTicket second = new ParkingTicket("TN02CD2222");

        first.display();
        second.display();

        System.out.println("Total Tickets: " + ParkingTicket.getCount());
    }
}
