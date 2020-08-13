package carproject.model;

import javafx.beans.property.SimpleStringProperty;

public class Parking {

    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty carBrand;
    private final SimpleStringProperty carColor;
    private final SimpleStringProperty plateNumber;
    private final SimpleStringProperty timeOfArrival;
   // private final SimpleStringProperty gender;

    public Parking() {
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.carBrand = new SimpleStringProperty();
        this.carColor = new SimpleStringProperty();
        this.plateNumber = new SimpleStringProperty();
        this.timeOfArrival = new SimpleStringProperty();
       // this.gender = new SimpleStringProperty();
    }
    /*
    public String getGender() {
        return gender.get();
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }
*/

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getCarBrand() {
        return carBrand.get();
    }

    public void setCarBrand(String carBrand) {
        this.carBrand.set(carBrand);
    }

    public String getCarColor() {
        return carColor.get();
    }
    
    public void setCarColor(String carColor) {
        this.carColor.set(carColor);
    }

    public String getPlateNumber() {
        return plateNumber.get();
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber.set(plateNumber);
    }

    public String getTimeOfArrival() {
        return timeOfArrival.get();
    }

    public void setTimeOfArrival(String timeOfArrival) {
        this.timeOfArrival.set(timeOfArrival);
    }
}
