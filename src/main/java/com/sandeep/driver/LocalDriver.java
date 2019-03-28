package com.sandeep.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

@Slf4j
class LocalDriver extends Driver {
    LocalDriver() { super(); }

    LocalDriver(String browser) {
        this();
        this.browser = browser;
    }

    /**
     * @see Driver#createDriver()
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
                options.addArguments("no-sandbox", "disable-dev-shm-usage", "disable-infobars", "incognito");
                if (System.getProperty("headless", config.getProperty("headless")).equalsIgnoreCase("true")) {
                    options.addArguments("window-size=1920, 1050", "headless");
                }
                //options.merge(sslError);
                driver = new ChromeDriver(options);
            } else if(browser.contains("firefox") || browser.contains("ff")) {
                WebDriverManager.firefoxdriver().version("0.23").setup();
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("-private");
                if (System.getProperty("headless", config.getProperty("headless")).equalsIgnoreCase("true")) {
                    options.addArguments("-width 1920", "-height 1080", "-headless");
                }
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
