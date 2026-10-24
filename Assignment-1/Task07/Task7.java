class Vehicle {
    private final String regNo;

    Vehicle(String regNo) {
        this.regNo = regNo;
    }

    double calculateFee() {
        return 20.0;
    }

    @Override
    public String toString() {
        return "Vehicle: " + regNo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vehicle)) return false;
        Vehicle other = (Vehicle) obj;
        return regNo.equals(other.regNo);
    }

    @Override
    public int hashCode() {
        return regNo.hashCode();
    }
}

class Car extends Vehicle {
    Car(String regNo) {
        super(regNo);
    }

    @Override
    double calculateFee() {
        return 30.0;
    }
}

public class Task7 {
    public static void main(String[] args) {
        Vehicle v = new Vehicle("TN01AB1111");
        Car c = new Car("TN02CD2222");

        System.out.println(v.calculateFee());
        System.out.println(c.calculateFee());
        System.out.println(c);
        System.out.println(new Car("TN02CD2222").equals(c));
    }
}
