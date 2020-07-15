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

import java.util.Properties;

import static com.sandeep.util.Utils.log_exception_and_fail;
import static java.lang.String.format;

@Slf4j
@Listeners({ScreenshotListener.class})
public abstract class BaseTestNGTest {
    private WebDriverFactory driverFactory;

    protected WebDriver driver;
    protected Properties config;

    @BeforeTest(alwaysRun = true)
    public void initTest (ITestContext testContext) {
        // create a WebDriver instance on the basis of the settings
        // provided in framework config properties file
        init_test_variables();
        driver.manage().window().maximize();
    }

    private void init_test_variables () {
        config = FrameworkConfig.getInstance().getConfigProperties();
        driverFactory = WebDriverFactory.getInstance();
        driver = driverFactory.getDriver(System.getProperty("driverType", config.getProperty("DRIVERTYPE")));
    }

    protected void loadApplication (ITestContext testContext) {
        driver.navigate().to(config.getProperty("url_dev"));
        initialize_landing_page(testContext);
    }

    private void initialize_landing_page (ITestContext testContext) {
        // initialize landing page object to null
        try {
            // initialize the object here
            if (driver != null)
                testContext.setAttribute(Context.PAGE_OBJECTS.GoogleHomePO.toString(), new GoogleHomePO(driver));
            else throw new NullPointerException("WebDriver object was not initialized!!!");
        } catch (NullPointerException npe) {
            init_test_variables();
            loadApplication(testContext);
            testContext.setAttribute(Context.PAGE_OBJECTS.GoogleHomePO.toString(), new GoogleHomePO(driver));
        } catch (Exception e) {
            log_exception_and_fail(format("unable to navigate to Dashboard page due to %s", e.getMessage()), e);
        }
    }

    @AfterTest(alwaysRun = true)
    public void teardownTest () {
        driverFactory.closeDriver();
    }
}
