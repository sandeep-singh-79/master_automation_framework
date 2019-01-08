package com.mckinsey.cucumber.stepdefinitions;

import com.mckinsey.cucumber.context.TestContext;
import com.mckinsey.cucumber.enums.Context;
import org.openqa.selenium.WebDriver;

public class BaseStepDefinition {
    protected WebDriver driver;
    protected TestContext context;

    public BaseStepDefinition (TestContext context) {
        this.context = context;
        driver = (WebDriver) context.getScenarioContext().getContext(Context.DRIVER.toString());
    }
}
