package com.sandeep.cucumber.context;

import com.sandeep.config.FrameworkConfig;
import com.sandeep.driver.WebDriverFactory;

import java.util.Properties;

public class TestContext {
    private WebDriverFactory webDriverManager;
    private ScenarioContext scenarioContext;
    private Properties config;

    public TestContext() {
        webDriverManager = WebDriverFactory.getInstance();
        scenarioContext = ScenarioContext.getInstance();
        config = FrameworkConfig.getInstance().getConfigProperties();
    }

    public Properties getConfig () {
        return config;
    }

    public WebDriverFactory getWebDriverManager() {
        return webDriverManager;
    }

    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }
}