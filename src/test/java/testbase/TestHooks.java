package testbase;


import io.cucumber.java.*;
import org.openqa.selenium.WebDriver;

public class TestHooks extends TestInitialise {

        WebDriver driver;

        @Before()
        public void TestInitialise(Scenario scenario) {
            try
            {
                InitialiseTest();
                ExtractScenarioName(scenario);
                StaticObjectRepo.sContext = new ScenarioContext(driver);
            }
            catch (Exception e)
            {
                System.out.println("Exception occured during Test Initialisation!! \n\n {0}" + e);
                CloseBrowser();
            }

        }

        @After(order=1)
        public void teardown() {
            System.out.println("\n Closing browser.......");
            CloseBrowser();
        }

        @BeforeStep
        public void beforeSteps(Scenario scenario) {
            //System.out.println("Executing scenario: " + scenario.getName() + " ----");
        }

        @AfterStep
        public void afterSteps(Scenario scenario) {
            AttachScreenshot();
        }

        @BeforeAll
        public static void before_all() {
            SetEnvironment();
        }

        @AfterAll
        public static void after_all() {
            //WriteToReport();
            UpdateReportProps();

        }


}
