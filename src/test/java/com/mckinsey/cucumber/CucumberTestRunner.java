package com.mckinsey.cucumber;

import com.mckinsey.config.FrameworkConfig;
import com.mckinsey.cucumber.context.TestContext;
import com.mckinsey.cucumber.enums.Context;
import com.mckinsey.driver.WebDriverFactory;
import com.mckinsey.pages.LoginPage;
import com.mckinsey.util.Utils;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import java.util.Properties;
import java.util.Random;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/features",
        glue = {"com.mckinsey.cucumber.stepdefinitions"},
        /*tags = {"@P1"},*/
        plugin = {
                "pretty", "html:target/cucumber-reports",
                "junit:target/cucumber-reports/Cucumber.xml",
                "json:target/cucumber-reports/Cucumber.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        monochrome = true
)
@Slf4j
public class CucumberTestRunner {
    private static String formattedDate = Utils.getTimeStamp("dd-MM-yyyy_HH_mm_ss");
    //private static ExtentProperties extentProperties;
    private static TestContext testContext;
    private static WebDriverFactory driverFactory;
    private static String driver_type;
    private static String browser;
    private static Properties config;
    private static int random_int = 0;
    private static LoginPage loginPage;

    public static WebDriver driver;

    @BeforeClass
    public static void setup() {
        initialize_runner_elements();
        String environment_url = launch_browser();

        navigate_to(environment_url);
        //Creating Extent Report
        //extentProperties = ExtentProperties.INSTANCE;
        //extentProperties.setReportPath(String.format("%s/target/Cucumber_Reports/Test_Report_%s%d.html", System.getProperty("user.dir"), formattedDate, random_int));
    }

    @AfterClass
    public static void tearDown () {
        write_extent_report();

        driverFactory.closeDriver();
    }

    private static void initialize_runner_elements () {
        config = FrameworkConfig.getInstance().getConfigProperties();
        testContext = new TestContext();
        driverFactory = testContext.getWebDriverManager().getInstance();
        driver_type = config.getProperty("DRIVERTYPE", "local");
        browser = System.getProperty("browser", "chrome");
        random_int = new Random().nextInt(10000);
    }

    private static String launch_browser () {
        //launching driver
        driver = driverFactory.getDriver(driver_type);
        log.info("Driver {} created for browser {}", driver_type.toUpperCase(), browser.toUpperCase());
        return config.getProperty(System.getProperty("env", "dev")
                                          .equalsIgnoreCase("dev")?"url_dev":"url_qa");
    }

    private static void navigate_to (String environment_url) {
        driver.navigate().to(environment_url);
        log.info("Loading URL: {}", environment_url);

        loginPage = new LoginPage(driver);
        testContext.getScenarioContext().setContext(Context.PAGE_OBJECTS.LOGIN.toString(), loginPage);
        /*if (environment_url.contains("McKinsey-WebSEAL")) {
            SsoLoginPage ssoLoginPage = new SsoLoginPage(driver);
            ssoLoginPage.login();
        }*/
    }

    private static void write_extent_report () {
        //log.info("Loading extent report config file:" + new File(System.getProperty("user.dir").replace("\\", "/") + FileReaderManager.getInstance().getConfigReader().getExtentConfigPath()));
        /*Reporter.loadXMLConfig(new File(String.format("%s/src/test/resources/extent-config.xml", System.getProperty("user.dir"))));
        Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
        Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
        Reporter.setSystemInfo("OS", String.format("%s %s", System.getProperty("os.name"), System.getProperty("os.arch")));
        Reporter.setSystemInfo("Selenium", "3.12");
        Reporter.setSystemInfo("Maven", "3.5.2");
        Reporter.setSystemInfo("Java Version", "1.8");
        Reporter.setSystemInfo("Browser", driver_type);*/

        // TODO: write the report file
    }
}