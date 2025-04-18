package testcases.schemas;


//Car class created to hold all the car details from the output file, inorder to compare
public class Car {

    private String registration;
    private String make;
    private String model;
    private String year;

    public Car(String registration, String make, String model, String year) {
        this.registration = registration;
        this.make = make;
        this.model = model;
        this.year = year;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
