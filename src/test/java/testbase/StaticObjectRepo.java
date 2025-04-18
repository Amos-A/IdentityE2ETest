package testbase;

import com.aventstack.extentreports.ExtentReports;

import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

import java.util.Properties;

public class StaticObjectRepo {

    public static WebDriver Driver;
    public static Scenario scenario;
    public static String FeatureName;
    public static ScenarioContext sContext;

    public static ExtentReports Reporter = new ExtentReports();

    public static String Environment;
    public static Properties prop;
    public static Properties envProp;
    public static String Error;
    public static String UserName;
    public static String Password;
    public static String Browser;

}
