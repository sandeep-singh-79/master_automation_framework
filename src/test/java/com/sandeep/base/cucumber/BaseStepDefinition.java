package com.sandeep.base.cucumber;

import com.sandeep.cucumber.context.TestContext;
import com.sandeep.cucumber.enums.Context;
import org.openqa.selenium.WebDriver;

public class BaseStepDefinition {
    protected WebDriver driver;
    protected TestContext context;

    public BaseStepDefinition (TestContext context) {
        this.context = context;
        driver = (WebDriver) context.getScenarioContext().getContext(Context.DRIVER.toString());
    }
}
