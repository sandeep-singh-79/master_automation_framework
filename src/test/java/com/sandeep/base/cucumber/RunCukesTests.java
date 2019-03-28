package com.sandeep.base.cucumber;

import com.sandeep.config.FrameworkConfig;
import com.sandeep.cucumber.context.TestContext;
import com.sandeep.driver.WebDriverFactory;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.util.Properties;

@CucumberOptions(
        features = "src/teste/resources/features",
        glue = {"com.sandeep.cucumber", "com.sandeep.base.cucumber"},
        /*tags = {"@P1"},*/
        plugin = {
                "pretty", "html:target/reports/cucumber",
                "junit:target/reports/cucumber/Cucumber.xml",
                "json:target/reports/cucumber/Cucumber.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        monochrome = true
)
@Slf4j
public class RunCukesTests extends AbstractTestNGCucumberTests {
    private TestContext testContext;
    private WebDriverFactory driverFactory;
    private String driver_type;
    private String browser;
    private Properties config;

    @BeforeClass
    public void setup() {
        initialize_runner_elements();
        String environment_url = config.getProperty(System.getProperty("env", "dev")
                .equalsIgnoreCase("dev") ? "url_dev" : "url_qa");
        launch_browser();
        testContext.getScenarioContext().setContext("url", environment_url);
    }

    @AfterClass
    public void tearDown () {
        driverFactory.closeDriver();
    }

    @DataProvider
    @Override
    public Object[][] scenarios() {
        return super.scenarios();
    }

    private void initialize_runner_elements () {
        config = FrameworkConfig.getInstance().getConfigProperties();
        testContext = new TestContext();
        driverFactory = WebDriverFactory.getInstance();
        driver_type = System.getProperty("driverType", config.getProperty("DRIVERTYPE"));
        browser = System.getProperty("browser", config.getProperty("BROWSER"));
    }

    private void launch_browser () {
        log.info("Driver {} created for browser {}", driver_type.toUpperCase(), browser.toUpperCase());
        WebDriver driver = driverFactory.getDriver(driver_type);
        driver.manage().window().maximize();
    }
}