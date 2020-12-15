package com.sandeep.base.cucumber;

import com.sandeep.cucumber.context.TestContext;
import com.sandeep.cucumber.enums.Context;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.sandeep.cucumber", "com.sandeep.base.cucumber"},
        /*tags = {"@P1"},*/
        plugin = {
                "pretty", "html:target/reports/cucumber",
                "junit:target/reports/cucumber/Cucumber.xml",
                "json:target/reports/cucumber/Cucumber.json"/*,
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"*/},
        monochrome = true
)
@Slf4j
public class RunCukesTests extends AbstractTestNGCucumberTests {
    private TestContext testContext;

    @BeforeClass
    public void setup() {
        testContext = new TestContext();
    }

    @AfterClass
    public void tearDown () {
        WebDriver driver = (WebDriver) testContext.getScenarioContext().getContext(Context.DRIVER.toString());
        if (driver != null) {
            driver.quit();
        }
    }

    @DataProvider(parallel = true)
    @Override
    public Object[][] scenarios() {
        return super.scenarios();
    }
}