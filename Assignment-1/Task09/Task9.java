interface Chargeable {
    double chargingFee(int units);
}

abstract class Vehicle {
    protected final String regNo;

    Vehicle(String regNo) {
        this.regNo = regNo;
    }

    abstract double calculateFee(int hours);

    void display() {
        System.out.println("Registration: " + regNo);
    }
}

class Car extends Vehicle {
    Car(String regNo) {
        super(regNo);
    }

    @Override
    double calculateFee(int hours) {
        return hours * 30.0;
    }
}

class ElectricCar extends Vehicle implements Chargeable {
    ElectricCar(String regNo) {
        super(regNo);
    }

    @Override
    double calculateFee(int hours) {
        return hours * 30.0;
    }

    @Override
    public double chargingFee(int units) {
        return units * 12.0;
    }
}

public class Task9 {
    public static void main(String[] args) {
        Vehicle car = new Car("TN01AB1111");
        car.display();
        System.out.println("Parking Fee: Rs " + car.calculateFee(3));

        ElectricCar ev = new ElectricCar("TN02CD2222");
        ev.display();
        System.out.println("Parking Fee: Rs " + ev.calculateFee(3));
        System.out.println("Charging Fee: Rs " + ev.chargingFee(10));
    }
}
