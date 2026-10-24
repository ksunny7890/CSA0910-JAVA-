class Vehicle {
    String vehicleNo;
    String type;
    long entryTime;

    Vehicle(String vehicleNo, String type, long entryTime) {
        this.vehicleNo = vehicleNo;
        this.type = type;
        this.entryTime = entryTime;
    }

    void display() {
        System.out.println("Vehicle Number: " + vehicleNo);
        System.out.println("Vehicle Type: " + type);
        System.out.println("Entry Time: " + entryTime);
    }
}

class ParkingTicket {
    int ticketNo;
    Vehicle vehicle;

    ParkingTicket(int ticketNo, Vehicle vehicle) {
        this.ticketNo = ticketNo;
        this.vehicle = vehicle;
    }

    void displayTicket() {
        System.out.println("Ticket Number: " + ticketNo);
        vehicle.display();
    }
}

public class Task1 {
    public static void main(String[] args) {
        Vehicle vehicle = new Vehicle(
            "TN01AB1111", "Car", System.currentTimeMillis()
        );

        ParkingTicket ticket = new ParkingTicket(1, vehicle);
        ticket.displayTicket();
    }
}
