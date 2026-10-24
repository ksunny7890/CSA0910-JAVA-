class ParkingSlot {
    private final int slotNo;
    private String vehicleNo;

    ParkingSlot(int slotNo) {
        if (slotNo < 1) {
            throw new IllegalArgumentException("Slot number must be positive");
        }
        this.slotNo = slotNo;
    }

    public int getSlotNo() {
        return slotNo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void park(String vehicleNo) {
        if (vehicleNo == null || vehicleNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle number is required");
        }
        if (this.vehicleNo != null) {
            throw new IllegalStateException("Slot is already occupied");
        }
        this.vehicleNo = vehicleNo;
    }

    public boolean release() {
        if (vehicleNo == null) {
            return false;
        }
        vehicleNo = null;
        return true;
    }
}

public class Task10 {
    public static void main(String[] args) {
        ParkingSlot slot = new ParkingSlot(1);
        slot.park("TN01AB1111");
        System.out.println("Slot: " + slot.getSlotNo());
        System.out.println("Vehicle: " + slot.getVehicleNo());
        System.out.println("Released: " + slot.release());
        System.out.println("Released again: " + slot.release());
    }
}
