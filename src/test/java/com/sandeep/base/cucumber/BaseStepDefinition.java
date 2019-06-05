package com.sandeep.base.cucumber;

import com.sandeep.config.PropertyReader;
import com.sandeep.cucumber.context.TestContext;
import com.sandeep.cucumber.enums.Context;
import com.sandeep.pages.LoginPage;
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

    protected LoginPage login_page;

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

    protected void retrieve_landing_page_object () {
        // initialize the landing page instance from the TextContext object
        if (context.getScenarioContext().contains(Context.PAGE_OBJECTS.LOGIN.toString()))
            login_page = (LoginPage) context.getScenarioContext().getContext(Context.PAGE_OBJECTS.LOGIN.toString());
        else {
            login_page = new LoginPage(driver);
            context.getScenarioContext().setContext(Context.PAGE_OBJECTS.LOGIN.toString(), login_page);
        }
    }
}
