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
        ArrayList<String>  listOfRegs = utility.getRegsFromFile();  //getRegsFromfile method that returns the registrations based on patterns from input file
        StaticObjectRepo.sContext.setContext("listOfRegs", listOfRegs);
        StaticObjectRepo.sContext.setContext("sizeofRegs", listOfRegs.size());

    }

    @And("I have a car output file to compare results")
    public void iHaveACarOutputFileToCompareResults() throws IOException {
        UtilityClass utility = new UtilityClass();
        ArrayList<Car> listOfCars = utility.getCarsFromFile(); //getCarsFromfile method that returns the list of cars from output file
        StaticObjectRepo.sContext.setContext("listOfCars", listOfCars);
    }


    @Given("I navigate to the motorway url")
    public void iNavigateToTheMotorwayUrl() {
        //Method to navigate to the url from config file
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
        if(!(Boolean)StaticObjectRepo.sContext.getContext("invalid_ith")) {
            carMileagePage.validateHeader();
        }
    }


    @When("I enter the {string} registration number from list and click value my car button")
    public void iEnterTheRegistrationNumberFromListAndClickValueMyCarButton(String ith) throws IOException {
        Object listOfRegs = (StaticObjectRepo.sContext.getContext("listOfRegs"));

        if((Integer) (StaticObjectRepo.sContext.getContext("sizeofRegs")) >= Integer.parseInt(ith)) {
            sellMyCarPage.searchCarDetails(((ArrayList<String>) listOfRegs).get(Integer.parseInt(ith) - 1));
            StaticObjectRepo.sContext.setContext("invalid_ith", false);
        }
        else{
            StaticObjectRepo.sContext.setContext("invalid_ith", true);
            System.out.println("\n\nSCENARIO SKIPPED! \n\n As number provided '"+ ith +" is more than records in input file\n\n");
            //if input provided in examples in feature file is more than the number of regs found in input file,
            // eg: 5 then the scenario is skipped instead of failing
        }
    }


    @And("the details of the car should match the expected from {string} registration in test file")
    public void theDetailsOfTheCarShouldMatchTheExpectedFromRegistrationInTestFile(String ith) {
        if(!(Boolean)StaticObjectRepo.sContext.getContext("invalid_ith")) {
            carMileagePage.validateCarDetails(Integer.parseInt(ith));
        }
    }
}