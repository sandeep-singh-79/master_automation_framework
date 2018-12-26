package com.sandeep.base;

import com.sandeep.config.FrameworkConfig;
import com.sandeep.driver.WebDriverFactory;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.Properties;

import static java.lang.Integer.parseInt;

@Slf4j
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
