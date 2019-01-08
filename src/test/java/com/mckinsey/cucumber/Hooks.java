package com.mckinsey.cucumber;

import com.mckinsey.config.FrameworkConfig;
import com.mckinsey.cucumber.context.TestContext;
import com.mckinsey.util.Utils;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.Properties;

@Slf4j
public class Hooks {
    TestContext testContext;
    private WebDriver driver;
    private Properties config;

    public Hooks(TestContext context) {
        testContext = context;
        config = FrameworkConfig.getInstance().getConfigProperties();
        driver = context.getWebDriverManager().getDriver(config.getProperty("DRIVERTYPE", "local"));
    }

    @Before
    public void beforeScenario(Scenario scenario) {}

    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        log.info("Scenario: {} completed with result: {}", scenario.getName(), scenario.getStatus());
        if (scenario.isFailed()) {
            generate_screenshot(scenario);
        }
    }

    private void generate_screenshot (Scenario scenario) {
        String scenario_name = scenario.getName().replaceAll(" ", "_");
        log.error("Generating screenshot on failure for scenario: {}", scenario_name);

        File screen_shot = Utils.take_screenshot(driver, scenario_name);

        //This attach the specified screenshot to the test
        //Reporter.addScreenCaptureFromPath(screen_shot.getPath());
    }
}
