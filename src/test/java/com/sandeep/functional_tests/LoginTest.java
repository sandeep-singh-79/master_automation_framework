package com.sandeep.functional_tests;

import com.sandeep.base.BaseTestNGTest;
import com.sandeep.pages.LoginErrorPage;
import com.sandeep.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LoginTest extends BaseTestNGTest {
    private LoginPage loginPage;
    private LoginErrorPage loginErrorPage;

    @BeforeMethod
    public void setupTest(ITestContext testContext) {
        this.driver = (WebDriver)testContext.getAttribute("driver");

        loadApplication();

        loginPage = new LoginPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    @Test
    public void verifyUserErrorsOutOnEnteringWrongCredentials() {
        loginErrorPage = loginPage.attemptSignIn("test", "password");

        assertThat(loginErrorPage.getErrorText(), is("Sorry, your account is disabled. Please contact LEVERTON Support for assistance."));
    }

}
