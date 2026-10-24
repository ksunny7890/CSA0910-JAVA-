public class Task2 {
    public static void main(String[] args) {
        // Example: 3 floors, 5 slots on each floor.
        String[][] slots = new String[3][5];

        int slotNumber = 1;
        String vehicleNumber = "TN01AB1111";
        long entryTime = System.currentTimeMillis();
        int parkingMinutes = 130;
        double hourlyRate = 30.0;

        // Store the vehicle in floor index 0, slot index 0.
        slots[0][0] = vehicleNumber;

        // Charge every started hour in full.
        int billableHours = (parkingMinutes + 59) / 60;
        double fee = billableHours * hourlyRate;

        System.out.println("Slot Number: " + slotNumber);
        System.out.println("Vehicle Number: " + slots[0][0]);
        System.out.println("Entry Time: " + entryTime);
        System.out.println("Billable Hours: " + billableHours);
        System.out.println("Parking Fee: Rs " + fee);
    }
}
