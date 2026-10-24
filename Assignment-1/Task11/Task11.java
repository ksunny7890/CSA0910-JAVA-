import java.util.HashMap;
import java.util.Map;

class ParkingTicket {
    private static int nextTicketNo = 1;

    final int ticketNo;
    final String vehicleNo;
    final String type;
    final int minutes;

    ParkingTicket(String vehicleNo, String type, int minutes) {
        if (vehicleNo == null || vehicleNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle number is required");
        }
        if (minutes < 0) {
            throw new IllegalArgumentException("Minutes cannot be negative");
        }
        this.ticketNo = nextTicketNo++;
        this.vehicleNo = vehicleNo;
        this.type = type;
        this.minutes = minutes;
    }

    double fee() {
        int hours = (minutes + 59) / 60;
        double rate;
        switch (type) {
            case "TwoWheeler": rate = 10.0; break;
            case "Car": rate = 30.0; break;
            case "ElectricCar": rate = 30.0; break;
            default: throw new IllegalArgumentException("Unknown vehicle type");
        }
        return hours * rate;
    }
}

public class Task11 {
    public static void main(String[] args) {
        Map<String, ParkingTicket> active = new HashMap<String, ParkingTicket>();

        active.put("TN01A1", new ParkingTicket("TN01A1", "TwoWheeler", 60));
        active.put("TN02B2", new ParkingTicket("TN02B2", "Car", 130));
        active.put("TN03C3", new ParkingTicket("TN03C3", "ElectricCar", 0));

        double total = 0;
        for (ParkingTicket ticket : active.values()) {
            double fee = ticket.fee();
            total += fee;
            System.out.println(ticket.vehicleNo + " -> Rs " + fee);
        }
        System.out.println("Total collection: Rs " + total);

        // Duplicate registration test: do not overwrite an active ticket.
        boolean duplicateRejected = active.containsKey("TN01A1");
        if (!duplicateRejected) {
            active.put("TN01A1",
                new ParkingTicket("TN01A1", "Car", 60));
        }
        System.out.println("Duplicate registration rejected: "
            + duplicateRejected);

        try {
            new ParkingTicket("TN04D4", "Truck", 60).fee();
        } catch (IllegalArgumentException ex) {
            System.out.println("Unknown type rejected: true");
        }

        try {
            new ParkingTicket("TN05E5", "Car", -1);
        } catch (IllegalArgumentException ex) {
            System.out.println("Negative duration rejected: true");
        }
    }
}
