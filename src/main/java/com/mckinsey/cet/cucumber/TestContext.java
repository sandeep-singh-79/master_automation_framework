package com.mckinsey.cet.cucumber;

import com.mckinsey.cet.driver.WebDriverFactory;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestContext {

    private WebDriverFactory webDriverManager;
    private ScenarioContext scenarioContext;

    public TestContext() {
        webDriverManager = WebDriverFactory.getInstance();
        scenarioContext = new ScenarioContext();
    }

    public WebDriverFactory getWebDriverManager() {
        return webDriverManager;
    }

    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }
}