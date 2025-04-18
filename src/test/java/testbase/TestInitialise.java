package testbase;

import com.aventstack.extentreports.service.ExtentService;
import io.cucumber.java.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import static testbase.StaticObjectRepo.*;

public class TestInitialise {

    public ScenarioContext scenarioContext;
    public WebDriver driver;


    public TestInitialise() {
    }

    public TestInitialise(ScenarioContext _scenarioContext) throws IOException {
        driver = StaticObjectRepo.Driver;
        this.scenarioContext = _scenarioContext;
    }

    public void InitialiseTest() throws IOException {
        System.out.println("\n Initialising Test.................");
        InitialiseDriver();
        SetDriverProps();
    }

    public static void SetEnvironment() {
        System.out.println("\n Initialising Environment.................");
        try {
            prop = new Properties();
            prop.load(new FileInputStream("src/test/resources/properties/config.properties"));
            StaticObjectRepo.Environment = prop.getProperty("environment");
            envProp = new Properties();
            envProp.load(new FileInputStream("src/test/resources/properties/environment/" + StaticObjectRepo.Environment + ".properties"));
            StaticObjectRepo.Browser = envProp.getProperty("Browser");
        } catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
        }
        System.out.println("\n Stripts running in " + StaticObjectRepo.Environment + " Environment");
    }


    private void InitialiseDriver() throws IOException {

        System.out.println("\n Initialising local Driver.................");
        String browser = ConfigReader.GetConfigValue("Browser").toLowerCase();
        switch (browser.toLowerCase()) {
            case "chrome" -> {
                System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
                StaticObjectRepo.Driver = GetChromeDriver();
            }
            case "firefox" -> {
                System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
                StaticObjectRepo.Driver = GetFirefoxDriver();
            }
            case "edge", "ms edge", "microsoft edge" -> {
                System.setProperty("webdriver.edge.driver", "src/test/resources/drivers/msedgedriver.exe");
                StaticObjectRepo.Driver = GetEdgeDriver();
            }
            default -> {
                System.out.println("Invalid value for browser in config file: {0}" + browser);
                throw new RuntimeException("Invalid value for browser in config file");
            }
        }

    }


    public void SetDriverProps() {
        System.out.println("\n Setting driver Properties.................");
        StaticObjectRepo.Driver.manage().window().maximize();
        StaticObjectRepo.Driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Long.parseLong(envProp.getProperty("PAGE_LOAD_TIMEOUT"))));
        System.out.println("\n Page Load Timeout set to " + envProp.getProperty(("PAGE_LOAD_TIMEOUT")));
        StaticObjectRepo.Driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(envProp.getProperty("IMPLICIT_WAIT"))));
        System.out.println("\n Implicit wait Timeout set to " + envProp.getProperty("IMPLICIT_WAIT"));
        driver = StaticObjectRepo.Driver;
    }


    private static EdgeDriver GetEdgeDriver() {
        System.out.println("\n Getting Edge driver.................");
        return new EdgeDriver(GetEdgeOptions());
    }

    private static ChromeDriver GetChromeDriver() {
        System.out.println("\n Getting Chrome driver.................");
        return new ChromeDriver(GetChromeOptions());
    }

    private static FirefoxDriver GetFirefoxDriver() {
        System.out.println("\n Getting Firefox driver.................");
        return new FirefoxDriver(GetFirefoxOptions());
    }

    private static FirefoxOptions GetFirefoxOptions() {
        System.out.println("\n Firefox Driver running in normal mode");
        FirefoxOptions foptions = new FirefoxOptions();
        foptions.setAcceptInsecureCerts(true);
        foptions.addArguments("start-maximized");
        return foptions;
    }

    private static EdgeOptions GetEdgeOptions() {
        System.out.println("\n Setting Edge options.................");
        EdgeOptions edgeOptions = new EdgeOptions();
        if (envProp.getProperty("Headless").equalsIgnoreCase("true")) {
            System.out.println("\n Edge Driver running in headless mode");
            edgeOptions.addArguments("headless");
            edgeOptions.addArguments("disable-gpu");
        } else {
            edgeOptions.setAcceptInsecureCerts(true);
            edgeOptions.addArguments("--disable-extensions");
            edgeOptions.addArguments("--no-default-browser-check");
            edgeOptions.addArguments("start-maximized");
            edgeOptions.addArguments("--enable-precise-memory-info");
            edgeOptions.addArguments("--disable-popup-blocking");
            edgeOptions.addArguments("--remote-allow-origins=*");
            System.out.println("\n Edge Driver running in normal mode");
        }

        if (envProp.getProperty("ExistingProfile").equalsIgnoreCase("yes")) {
            edgeOptions.addArguments("user-data-dir=" + envProp.getProperty("Profilepath"));
            edgeOptions.addArguments("profile-directory=" + envProp.getProperty("Profilefolder"));
            System.out.println("\n Edge Driver running using existing profile: " + envProp.getProperty("Profilefolder"));
        }

        return edgeOptions;
    }

    private static ChromeOptions GetChromeOptions() {
        System.out.println("\n Setting Chrome options.................");
        ChromeOptions chromeOptions = new ChromeOptions();
        if (envProp.getProperty("Headless").equalsIgnoreCase("true")) {
            chromeOptions.addArguments("headless");
            chromeOptions.addArguments("--remote-allow-origins=*");
            System.out.println("\n Chrome Driver running in headless mode");
        } else {
            chromeOptions.addArguments("--disable-extensions");
            chromeOptions.addArguments("--no-default-browser-check");
            chromeOptions.addArguments("start-maximized");
            chromeOptions.addArguments("--enable-precise-memory-info");
            chromeOptions.addArguments("--disable-popup-blocking");
            chromeOptions.addArguments("--remote-allow-origins=*");
            System.out.println("\n Chrome Driver running in normal mode");
        }

        return chromeOptions;
    }

    public static void CloseBrowser() {
        //System.out.println("Closing Browser.................");
        if (StaticObjectRepo.Driver != null) {
            StaticObjectRepo.Driver.quit();
        }
    }

    public String GetURL(String role, String env) {
        //System.out.println("\n Getting Application URL for environment.................");
        String result = "";
        String text = "Url";
        String text2 = env;
        if (env.isEmpty()) {
            text2 = StaticObjectRepo.Environment.toLowerCase();
        }

        if (!role.isEmpty()) {
            text = role + "Url";
        }

        if (!text2.isEmpty()) {
            result = envProp.getProperty(text2 + text);
        } else {
            System.out.println("Valid URL not found in Config file!!");
            CloseBrowser();
        }

        return result;
    }

    public String GetURL() {
        String result = "";
        System.out.println("\n Getting Application URL for environment.................");
        try {
            result = envProp.getProperty(Environment + "Url");
        } catch (Exception exception) {
            System.out.println("Valid URL not found in Config file!!");
            System.out.println("Exception :\n" + exception);
            CloseBrowser();
        }
        return result;
    }

    public static void UpdateReportProps() {
        Capabilities cap = ((RemoteWebDriver) StaticObjectRepo.Driver).getCapabilities();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm");
        Date dateTime = new Date();
        ExtentService.getInstance().setSystemInfo("Tester", System.getProperty("user.name"));
        ExtentService.getInstance().setSystemInfo("OS", System.getProperty("os.name"));
        ExtentService.getInstance().setSystemInfo("Browser", StaticObjectRepo.Browser.toUpperCase());
        ExtentService.getInstance().setSystemInfo("Browser Version", cap.getBrowserVersion());
        ExtentService.getInstance().setSystemInfo("Environment", StaticObjectRepo.Environment.toUpperCase());
        ExtentService.getInstance().setSystemInfo("Date", simpleDateFormat.format(dateTime));
        ExtentService.getInstance().setSystemInfo("Time", simpleTimeFormat.format(dateTime));
    }


    public static void ExtractFeatureName() {
        System.out.println("\n Getting Feature details.................");
    }

    public void ExtractScenarioName(Scenario scenario) {
        System.out.println("\n Getting Scenario details.................");
        StaticObjectRepo.scenario = scenario;
    }

    public static void WriteToReport() {
        System.out.println("\n Writing to report.................");
        StaticObjectRepo.Reporter.flush();
    }


    public static void AttachScreenshot() {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) Driver).getScreenshotAs((OutputType.BYTES));
            scenario.attach(screenshot, "image/png", "image");
        }
    }

}
