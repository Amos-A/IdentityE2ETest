package testcases.stepDefinations;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import testbase.BasePage;
import testbase.ScenarioContext;
import testbase.StaticObjectRepo;
import testcases.pages.CarMileagePage;
import testcases.pages.SellMyCarPage;
import testcases.schemas.Car;
import testcases.utilities.UtilityClass;

import static testbase.PageObjectRepo.*;

import java.io.File;
import java.io.IOException;
import java.sql.Driver;
import java.util.ArrayList;

public class validateCarDetailStepDefination {


    @Given("I have an input file to extract the registration numbers")
    public void iHaveAnInputFileToExtractTheRegistrationNumbers() throws IOException {
        UtilityClass utility = new UtilityClass();
        ArrayList<String>  listOfRegs = utility.getRegsFromFile();
        StaticObjectRepo.sContext.setContext("listOfRegs", listOfRegs);
    }

    @And("I have a car output file to compare results")
    public void iHaveACarOutputFileToCompareResults() throws IOException {
        UtilityClass utility = new UtilityClass();
        ArrayList<Car> listOfCars = utility.getCarsFromFile();
        StaticObjectRepo.sContext.setContext("listOfCars", listOfCars);
    }


    @Given("I navigate to the motorway url")
    public void iNavigateToTheMotorwayUrl() {
        BasePage basePage = null;
        try {
            basePage = new BasePage(StaticObjectRepo.Driver);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (basePage != null)
            basePage.GoToURL();
        else
            throw new RuntimeException("Base page is not initialised!!");
    }

    @Then("I should land on the SellMyCar Page")
    public void iShouldLandOnTheSellMyCarPage() throws IOException {
        sellMyCarPage = new SellMyCarPage(StaticObjectRepo.Driver);
        sellMyCarPage.validateHeader();
    }

    @Then("I should see the CarMileage page")
    public void iShouldSeeTheCarMileagePage() {
        carMileagePage.validateHeader();
    }


    @When("I enter the {string} registration number from list and click value my car button")
    public void iEnterTheRegistrationNumberFromListAndClickValueMyCarButton(String ith) throws IOException {
        Object listOfRegs = (StaticObjectRepo.sContext.getContext("listOfRegs"));
        sellMyCarPage.searchCarDetails(((ArrayList<String>) listOfRegs).get(Integer.parseInt(ith)-1));
    }


    @And("the details of the car should match the expected from {string} registration in test file")
    public void theDetailsOfTheCarShouldMatchTheExpectedFromRegistrationInTestFile(String ith) {
        carMileagePage.validateCarDetails(Integer.parseInt(ith));
    }
}