package testcases.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import testbase.BasePage;

import java.io.IOException;

import static testbase.PageObjectRepo.carMileagePage;


//Home page where Reg detail can be entered
public class SellMyCarPage extends BasePage {


    WebElement pageHeading = driver.findElement(By.cssSelector("h1"));
    WebElement valueButton = driver.findElement(By.cssSelector("button[type='submit']"));
    WebElement regNumberInput = driver.findElement(By.id("vrm-input"));

    public SellMyCarPage(WebDriver _driver) throws IOException {
        super(_driver);
        driver = _driver;
    }

    //Method to search car details using the values from input sheet
    public void searchCarDetails(String regNo) throws IOException {
            EnterText(regNumberInput, regNo);
            ClickOnElement(valueButton);
            carMileagePage = new CarMileagePage(driver);
    }


    public void validateHeader() {
        Assert.assertTrue("The header of Sell my car page does not match!", pageHeading.getText().startsWith("Sell my car."));
    }
}
