package cargoCompany;

import core.Human;
import core.Position;
import core.Vehicle;
import core.skills.DriveSkill;
import core.skills.EmployeeSkill;
import core.skills.PassengerSkill;

public class CargoCompanyEmployee extends Human implements EmployeeSkill, PassengerSkill, DriveSkill {
    public CargoCompanyEmployee(Position position, String name, int age, int cash) {
        super(position, name, age, cash);
    }

    public boolean receiveOffer(int amount) {
        return true;
    }

    public void receivePaycheck(int amount) {
        this.cash += amount;
    }

    public void sitInVehicle(Vehicle vehicle) {
        if (vehicle.hasEmptySeats()) {
            vehicle.addPassenger(this);
        } else {
            System.out.println("Failed to sit into the vehicle. No empty seats");
        }
    }

    public void leaveFromVehicle(Vehicle vehicle) {
        vehicle.removePassenger(this);
    }

    public void driveTo(Vehicle vehicle, Position position) {
        vehicle.move(position);
    }
}
