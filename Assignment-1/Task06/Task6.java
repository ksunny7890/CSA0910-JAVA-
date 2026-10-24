class Vehicle {
    protected String regNo;

    Vehicle(String regNo) {
        this.regNo = regNo;
        System.out.println("Vehicle constructor");
    }

    void display() {
        System.out.println("Vehicle: " + regNo);
    }
}

class Car extends Vehicle {
    Car(String regNo) {
        super(regNo);
        System.out.println("Car constructor");
    }
}

class ElectricCar extends Car {
    ElectricCar(String regNo) {
        super(regNo);
        System.out.println("ElectricCar constructor");
    }
}

public class Task6 {
    public static void main(String[] args) {
        ElectricCar e = new ElectricCar("TN03EF3333");
        e.display();
    }
}
