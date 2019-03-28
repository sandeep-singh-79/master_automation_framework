package com.sandeep.base.cucumber;

import com.sandeep.config.PropertyReader;
import com.sandeep.cucumber.context.TestContext;
import com.sandeep.cucumber.enums.Context;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.Properties;

public class BaseStepDefinition {
    protected TestContext context;
    protected WebDriver driver;
    protected Properties test_data;
    protected Properties config;
    protected Actions mouseOver;
    protected JavascriptExecutor js;
    protected String envir;

    public BaseStepDefinition (TestContext context) {
        this.context = context;
        driver = (WebDriver) context.getScenarioContext().getContext(Context.DRIVER.toString());

        test_data = new PropertyReader(new java.io.File(String.format("%s/src/test/resources/test_data/data.properties", System.getProperty("user.dir"))))
                .getPropertyFile();
        config = this.context.getConfig();
        mouseOver = new Actions(driver);
        js = (JavascriptExecutor) driver;
        envir = System.getProperty("env", config.getProperty("env"));
    }
}
