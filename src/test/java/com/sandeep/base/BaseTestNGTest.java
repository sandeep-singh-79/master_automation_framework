package com.sandeep.base;

import com.sandeep.config.FrameworkConfig;
import com.sandeep.cucumber.enums.Context;
import com.sandeep.driver.WebDriverFactory;
import com.sandeep.listeners.ScreenshotListener;
import com.sandeep.pages.GoogleHomePO;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import java.util.Arrays;
import java.util.Properties;

@Slf4j
@Listeners({ScreenshotListener.class})
public abstract class BaseTestNGTest {
    private WebDriverFactory driverFactory;

    protected WebDriver driver;
    protected Properties config;

    @BeforeTest(alwaysRun = true)
    public void initTest(ITestContext testContext) {
        // create a WebDriver instance on the basis of the settings
        // provided in framework config properties file
        config = FrameworkConfig.getInstance().getConfigProperties();
        driverFactory = WebDriverFactory.getInstance();
        driver = driverFactory.getDriver(System.getProperty("driverType", config.getProperty("DRIVERTYPE")));

        driver.manage().window().maximize();
        loadApplication(testContext);

        testContext.setAttribute("driver", driver);
    }

    protected void loadApplication (ITestContext testContext) {
        driver.navigate().to(config.getProperty("url_dev"));
        initialize_landing_page(testContext);
    }

    private void initialize_landing_page (ITestContext testContext) {
        // intialize landing page object to null
        try {
            // initialize the object here
        } catch (Exception e) {
            log.error("unable to navigate to Dashboard page due to {}", e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
        }

        // set the landing page object to test context object
        testContext.setAttribute(Context.PAGE_OBJECTS.GoogleHomePO.toString(), new GoogleHomePO(driver));
    }

    @AfterTest(alwaysRun=true)
    public void teardownTest() {
        driverFactory.closeDriver();
    }
}
