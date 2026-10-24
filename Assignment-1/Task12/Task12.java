import java.time.LocalTime;

interface PricingRule {
    double fee(int minutes, LocalTime entryTime);
}

interface Pass {
    boolean isValid();
}

class OneDayPass implements Pass {
    private final boolean valid;

    OneDayPass(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }
}

abstract class Vehicle {
    protected final String regNo;

    Vehicle(String regNo) {
        this.regNo = regNo;
    }

    abstract double calculateFee(int minutes, LocalTime entryTime);
}

class StandardVehicle extends Vehicle {
    private final PricingRule rule;

    StandardVehicle(String regNo, PricingRule rule) {
        super(regNo);
        this.rule = rule;
    }

    @Override
    double calculateFee(int minutes, LocalTime entryTime) {
        return rule.fee(minutes, entryTime);
    }
}

class EBike extends Vehicle {
    EBike(String regNo) {
        super(regNo);
    }

    @Override
    double calculateFee(int minutes, LocalTime entryTime) {
        return 15.0; // flat fee
    }
}

class DeliveryVan extends Vehicle {
    DeliveryVan(String regNo) {
        super(regNo);
    }

    @Override
    double calculateFee(int minutes, LocalTime entryTime) {
        int hours = (minutes + 59) / 60;
        double rate = 30.0;

        // Simplified rule: apply double car rate when entry is between 09:00 and 16:59.
        if (!entryTime.isBefore(LocalTime.of(9, 0))
                && entryTime.isBefore(LocalTime.of(17, 0))) {
            rate *= 2;
        }
        return hours * rate;
    }
}

public class Task12 {
    public static void main(String[] args) {
        // Jagged array: each floor may have a different number of slots.
        String[][] slots = {
            new String[2],
            new String[4],
            new String[3],
            new String[5],
            new String[1]
        };
        System.out.println("Number of floors: " + slots.length);

        PricingRule carRule = new PricingRule() {
            public double fee(int minutes, LocalTime entryTime) {
                int hours = (minutes + 59) / 60;
                return hours * 30.0;
            }
        };

        Vehicle car = new StandardVehicle("TN01AB1111", carRule);
        Vehicle bike = new EBike("TN02EB2222");
        Vehicle van = new DeliveryVan("TN03DV3333");

        System.out.println("Car fee: Rs "
            + car.calculateFee(120, LocalTime.of(10, 0)));
        System.out.println("E-bike flat fee: Rs "
            + bike.calculateFee(120, LocalTime.of(8, 0)));
        System.out.println("Delivery van fee: Rs "
            + van.calculateFee(120, LocalTime.of(10, 0)));

        Pass visitorPass = new OneDayPass(true);
        double visitorFee = visitorPass.isValid()
            ? 0.0 : car.calculateFee(120, LocalTime.of(10, 0));
        System.out.println("Visitor with valid one-day pass: Rs " + visitorFee);
    }
}
