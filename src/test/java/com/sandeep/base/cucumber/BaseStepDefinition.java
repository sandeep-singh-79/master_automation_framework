package com.sandeep.base.cucumber;

import com.sandeep.config.PropertyFileReader;
import com.sandeep.cucumber.context.TestContext;
import com.sandeep.cucumber.enums.Context;
import com.sandeep.pages.GoogleHomePO;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.util.Properties;

@Slf4j
public class BaseStepDefinition {
    protected TestContext context;
    protected WebDriver driver;
    protected Properties test_data;
    protected Properties config;
    protected Actions mouseOver;
    protected JavascriptExecutor js;
    protected String envir;
    // declare protected object of landing page
    protected GoogleHomePO ghPO;

    public BaseStepDefinition (TestContext context) {
        this.context = context;
        driver = (WebDriver) context.getScenarioContext().getContext(Context.DRIVER.toString());

        test_data = new PropertyFileReader(new File(String.format("%s/src/test/resources/test_data/data.properties", System.getProperty("user.dir"))))
                .getPropertyFile();
        config = this.context.getConfig();
        mouseOver = new Actions(driver);
        js = (JavascriptExecutor) driver;
        envir = System.getProperty("env", config.getProperty("env"));
    }

    protected void retrieve_landing_page_object () {
        // initialize the landing page instance from the TextContext object
        if (context.getScenarioContext().contains(Context.PAGE_OBJECTS.GoogleHomePO.toString()))
            ghPO = (GoogleHomePO) context.getScenarioContext().getContext(Context.PAGE_OBJECTS.GoogleHomePO.toString());
        else {
            ghPO = new GoogleHomePO(driver);
            context.getScenarioContext().setContext(Context.PAGE_OBJECTS.GoogleHomePO.toString(), ghPO);
        }
    }
}
