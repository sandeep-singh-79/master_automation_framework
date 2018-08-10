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
    protected Properties config = new FrameworkConfig().getConfigProperties();

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext testContext) throws Exception {
        // create a WebDriver instance on the basis of the settings
        // provided in framework config properties file
        driverFactory = new WebDriverFactory();
        driver = driverFactory.getDriver(config.getProperty("DRIVERTYPE"));

        driver.manage().timeouts().implicitlyWait(parseInt(config.getProperty("IMPLICITWAIT_TIMEOUT")),
                TimeUnit.MILLISECONDS);

        testContext.setAttribute("driver", driver);
    }

    protected void loadApplication() {
        driver.manage().window().maximize();

        driver.get(config.getProperty("url"));
    }

    @AfterClass(alwaysRun=true)
    public void afterClass() {
        if(driver != null) {
            driver.close();
            driver.quit();
        }
    }
}
