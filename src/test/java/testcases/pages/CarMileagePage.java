package testcases.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import testbase.BasePage;
import testbase.StaticObjectRepo;
import testcases.schemas.Car;

import java.io.IOException;
import java.util.ArrayList;

import static testbase.PageObjectRepo.sellMyCarPage;

public class CarMileagePage extends BasePage {


    WebElement heading = driver.findElement(By.cssSelector(".MileageInput__component-JKC6 > div"));
    WebElement carRegNumber = driver.findElement(By.cssSelector(".HeroVehicle__component-Av9f > div"));
    WebElement carMakeModel = driver.findElement(By.cssSelector("h1[data-cy='vehicleMakeAndModel']"));
    WebElement carYear = driver.findElement(By.cssSelector("ul[data-cy='vehicleSpecifics'] > li:nth-child(1)"));

    public CarMileagePage(WebDriver _driver) throws IOException {
        super(_driver);
        driver = _driver;
    }


    public void validateHeader(){
        Assert.assertEquals("Car details not found for reg: \n Car Mileage Page is not displayed!", "Your mileage", heading.getText());
    }


    public void validateCarDetails(int record){

        ArrayList<Car> cars = (ArrayList<Car>) StaticObjectRepo.sContext.getContext("listOfCars");
        ArrayList<String> listOfRegs = (ArrayList<String>) (StaticObjectRepo.sContext.getContext("listOfRegs"));
        String reg = listOfRegs.get(record-1);

        for(Car c : cars) {
            if(c.getRegistration().equals(reg)) {
                Assert.assertEquals("Car Reg does not match!", c.getRegistration(), carRegNumber.getText().toUpperCase());
                Assert.assertTrue("Car Model does not match!", carMakeModel.getText().toUpperCase().startsWith(c.getMake()));
                Assert.assertTrue("Car Make does not match!", carMakeModel.getText().toUpperCase().endsWith(c.getModel()));
                Assert.assertEquals("Car year does not match!", c.getYear(), carYear.getText().toUpperCase());
            }
        }
    }

}
