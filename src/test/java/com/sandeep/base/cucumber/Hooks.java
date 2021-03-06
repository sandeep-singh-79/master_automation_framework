package com.sandeep.base.cucumber;

import com.sandeep.cucumber.context.TestContext;
import com.sandeep.cucumber.enums.Context;
import com.sandeep.pages.GoogleHomePO;
import com.sandeep.util.Utils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Properties;

import static org.testng.Assert.fail;

@Slf4j
public class Hooks {
    private TestContext testContext;
    private WebDriver driver;
    private Properties config;

    public Hooks(TestContext context) {
        testContext = context;
        config = testContext.getConfig();
        driver = context.getWebDriverManager().getDriver(System.getProperty("driverType", config.getProperty("DRIVERTYPE")));
        context.getWebDriverManager().addDriver(driver);
    }

    @Before (order = 1)
    public void beforeScenario(Scenario scenario) {
        log.info("launching scenario {}...", scenario.getName());
        navigate_to(System.getProperty("env", "dev").equals("dev") ? config.getProperty("url_dev") : config.getProperty("url_qa"));
    }

    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        log.info("Scenario: {} completed with result: {}", scenario.getName(), scenario.getStatus());
        driver.manage().deleteAllCookies();
        if (scenario.isFailed() || scenario.getStatus().toString().equalsIgnoreCase("UNDEFINED")) {
            fail();
            logError(scenario);
            log.error("*******************Browser Log*******************");
            driver.manage().logs().get(LogType.BROWSER).forEach((entry) -> log.error(entry.getMessage()));
            log.error("*************************************************");
            scenario.attach(Utils.toByteArray(Utils.take_screenshot(driver, scenario.getName())), "image/png", scenario.getName());
        }
    }

    private static void logError (Scenario scenario) {
        Field field = FieldUtils.getField((scenario).getClass(), "stepResults", true);
        field.setAccessible(true);
        try {
            ArrayList <Result> results = (ArrayList <Result>) field.get(scenario);
            results.stream().filter(result -> result.getError() != null).forEach(result -> log.error("Error Scenario: {}", scenario.getId(), result.getError()));
        } catch (Exception e) {
            log.error("Error while logging error", e);
        }
    }

    private void navigate_to (String environment_url) {
        // launch browser
        log.info("Loading URL: {}", environment_url);
        driver.navigate().to(environment_url);
        driver.manage().window().maximize();

        // set the webdriver instance to the context object
        testContext.getScenarioContext().setContext(Context.DRIVER.toString(), driver);
        // set the landing page object in context object here
        testContext.getScenarioContext().setContext(Context.PAGE_OBJECTS.GoogleHomePO.toString(), new GoogleHomePO(driver));
    }
}
