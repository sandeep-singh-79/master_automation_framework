package ai.leverton.demo.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

class LocalDriver extends Driver {
    LocalDriver() { super(); }

    LocalDriver(String browser) {
        this();
        this.browser = browser;
    }

    /**
     * @see ai.leverton.demo.driver.Driver#createDriver()
     * Creates an instance of a browser webdriver based on
     * the browser name stored in the framework config file.
     */
    @Override
    public WebDriver createDriver() {
        /*DesiredCapabilities sslError = new DesiredCapabilities();
        sslError.setAcceptInsecureCerts(true);*/

        if (driver == null) {
            if(browser.contains("chrome")) {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                //options.merge(sslError);
                driver = new ChromeDriver(options);
            } else if(browser.contains("firefox") || browser.contains("ff")) {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                //options.merge(sslError);
                driver = new FirefoxDriver(options);
            } else if(System.getProperty("os.name").indexOf("win")==0 && (browser.contains("iexplore") || browser.contains("internet"))) {
                WebDriverManager.iedriver().setup();
                InternetExplorerOptions options = new InternetExplorerOptions();
                //options.merge(sslError);
                driver = new InternetExplorerDriver(options);
            }
        }

        return driver;
    }
}
