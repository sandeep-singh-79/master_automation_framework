package ai.leverton.demo.base;

import ai.leverton.demo.config.FrameworkConfig;
import ai.leverton.demo.driver.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

public abstract class BaseTestNGTest {
    private WebDriverFactory driverFactory;

    protected WebDriver driver;
    protected Properties config;

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext testContext) {
        // create a WebDriver instance on the basis of the settings
        // provided in framework config properties file
        config = FrameworkConfig.getInstance().getConfigProperties();
        driverFactory = WebDriverFactory.getInstance();
        driver = driverFactory.getDriver(System.getProperty("driverType", config.getProperty("DRIVERTYPE")));

        driver.manage().window().maximize();

        testContext.setAttribute("driver", driver);
    }

    protected void loadApplication() {
        driver.get(config.getProperty("url"));
    }

    @AfterClass(alwaysRun=true)
    public void afterClass() {
        driverFactory.closeDriver();
    }
}
