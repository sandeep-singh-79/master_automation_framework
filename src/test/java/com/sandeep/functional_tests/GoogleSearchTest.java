package com.sandeep.functional_tests;

import com.sandeep.base.BaseTestNGTest;
import com.sandeep.cucumber.enums.Context;
import com.sandeep.pages.GoogleHomePO;
import com.sandeep.pages.GoogleSearchPO;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

import static org.testng.Assert.assertTrue;

@Slf4j
@Listeners({com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter.class})
public class GoogleSearchTest extends BaseTestNGTest {
    private GoogleHomePO ghPO;
    private GoogleSearchPO gsPO;

    @DataProvider
    public static Object[][] provide_search_terms () {
        return new Object[][]{{"Cucumber"}, {"Selenium"}, {"Eclipse"}};
    }

    @BeforeMethod
    public void setupTest(ITestContext testContext) {
        this.driver = (WebDriver)testContext.getAttribute("driver");

        ghPO = (GoogleHomePO) testContext.getAttribute(Context.PAGE_OBJECTS.GoogleHomePO.toString());
    }

    @AfterMethod(alwaysRun = true)
    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    @Test(dataProvider = "provide_search_terms")
    public void Google_Search_For_Search_Term_T (String search_term) {
        gsPO = ghPO.performSearch(search_term);
        byte search_result_count = (byte) gsPO.searchResultCount();
        log.info("\nCount results for {} search is {}.\n", search_term, search_result_count);
        assertTrue(search_result_count > 1);
    }
}
