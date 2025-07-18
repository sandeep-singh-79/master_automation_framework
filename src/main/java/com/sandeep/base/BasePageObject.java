package com.sandeep.base;

import com.sandeep.config.FrameworkConfig;
import com.sandeep.config.PropertyFileReader;
import com.sandeep.util.Ajax_JS_Load_Wait;
import com.sandeep.util.WebPageAction;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

@Slf4j
public abstract class BasePageObject {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected AjaxElementLocatorFactory ajaxElementLocatorFactory;
    protected Properties test_data;
    protected WebPageAction page_action;
    private Ajax_JS_Load_Wait wait_for_js_to_load;
    private Properties config;

    public BasePageObject(WebDriver driver) {
        this(driver, FrameworkConfig.getInstance().getConfigProperties());
    }

    public BasePageObject(WebDriver driver, Properties config) {
        this.driver = driver;
        this.config = config;
        wait = new WebDriverWait(driver, parseInt(config.getProperty("WEBDRIVERWAIT_TIMEOUT")),
                parseInt(config.getProperty("WEBDRIVERWAIT_POLL")));
        ajaxElementLocatorFactory = new AjaxElementLocatorFactory(driver, parseInt(config.getProperty("LOCATOR_FACTORY_TIMEOUT")));
        test_data = new PropertyFileReader(new File(String.format("%s/src/test/resources/test_data/data.properties", System.getProperty("user.dir"))))
                .getPropertyFile();
        page_action = new WebPageAction(this, driver);
        wait_for_js_to_load = new Ajax_JS_Load_Wait(driver, wait);
        setTimeouts();

        isLoaded();
    }

    private void setTimeouts() {
        driver.manage().timeouts().implicitlyWait(parseInt(config.getProperty("IMPLICITWAIT_TIMEOUT")),
                TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(parseInt(config.getProperty("PAGE_LOAD_TIMEOUT")),
                TimeUnit.SECONDS);
    }

    /**
     * Each page object must implement this method to return the identifier of a unique WebElement on that page.
     * The presence of this unique element will be used to assert that the expected page has finished loading
     *
     * @return the By locator of unique element(s) on the page
     */
    protected abstract By getUniqueElement();

    private void isLoaded () throws Error {
        //Define a list of WebElements that match the unique element locator for the page
        By uniqElement = getUniqueElement();
        List<WebElement> uniqueElement = driver.findElements(uniqElement);

        // Assert that the unique element is present in the DOM
        // Log DOM info if element is missing
        if (uniqueElement.isEmpty()) {
            logLoadFailureDetails();  // <-- call diagnostic hook before failing
            Assert.fail(String.format("❌ Unique Element %s not found for %s",
                uniqElement.toString(), this.getClass().getSimpleName()));
        }

        // Wait until the unique element is visible in the browser and ready to use. This helps make sure the page is
        // loaded before the next step of the tests continue.
        if (wait_for_js_to_load.wait_for_ajax_to_finish()) {
            wait.until(ExpectedConditions.visibilityOfAllElements(uniqueElement));
        }
    }

    public boolean is_page_ajax_loaded () {
        return wait_for_js_to_load.wait_for_ajax_to_finish();
    }

    public WebPageAction get_page_action () {
        return page_action;
    }

    protected void logLoadFailureDetails() {
        try {
            System.err.println("❌ Page load failure in: " + this.getClass().getSimpleName());
            System.err.println("Title: " + driver.getTitle());
            System.err.println("URL: " + driver.getCurrentUrl());
            String dom = driver.getPageSource();
            System.err.println("Page contains 'captcha'? " + dom.toLowerCase().contains("captcha"));
            System.err.println("Partial DOM snapshot:\n" + dom.substring(0, Math.min(500, dom.length())));
        } catch (Exception e) {
            System.err.println("⚠️ Failed to log page diagnostics: " + e.getMessage());
        }
    }
}
