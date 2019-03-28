package com.sandeep.base.cucumber;

import com.sandeep.config.FrameworkConfig;
import com.sandeep.cucumber.context.TestContext;
import com.sandeep.cucumber.enums.Context;
import com.sandeep.pages.LoginPage;
import com.sandeep.util.Utils;
import cucumber.api.Result;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Properties;

@Slf4j
public class Hooks {
    private TestContext testContext;
    private WebDriver driver;

    public Hooks(TestContext context) {
        testContext = context;
        Properties config = FrameworkConfig.getInstance().getConfigProperties();
        driver = context.getWebDriverManager().getDriver(config.getProperty("DRIVERTYPE", "local"));
    }

    @Before (order = 1)
    public void beforeScenario(Scenario scenario) {
        log.info("launching scenario {}...", scenario.getName());
        navigate_to((String) testContext.getScenarioContext().getContext("url"));
    }

    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        log.info("Scenario: {} completed with result: {}", scenario.getName(), scenario.getStatus());
        if (scenario.isFailed()) {
            logError(scenario);
            log.error("*******************Browser Log*******************");
            driver.manage().logs().get(LogType.BROWSER).forEach((entry) -> log.error(entry.getMessage()));
            log.error("*************************************************");
            Utils.take_screenshot(driver, scenario.getName());
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
        log.info("Loading URL: {}", environment_url);
        driver.navigate().to(environment_url);

        LoginPage loginPage = new LoginPage(driver);
        testContext.getScenarioContext().setContext(Context.PAGE_OBJECTS.LOGIN.toString(), loginPage);
    }
}
