package com.sandeep.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.io.File;
import java.util.Map;

@Slf4j
class LocalDriver implements IDriver {
    private WebDriver driver;
    private final String browser;
    private final String headless;

    LocalDriver(final String browser, final String headless) {
        this.browser = browser;
        this.headless = headless;
    }

    private boolean isRunningInDocker() {
        return new File("/.dockerenv").exists();
    }


    /**
     * @see IDriver#createDriver()
     * Creates an instance of a browser webdriver based on
     * the browser name stored in the framework config file.
     */
    @Override
    public WebDriver createDriver() {
        /*DesiredCapabilities sslError = new DesiredCapabilities();
        sslError.setAcceptInsecureCerts(true);*/

        if (driver == null) {
            if(browser.contains("chrome")) {
                String chromeDriverVersion = System.getProperty("CHROMEDRIVER_VERSION",
                    System.getenv().getOrDefault("CHROMEDRIVER_VERSION", "137.0.7151.70"));


                if (isRunningInDocker() && chromeDriverVersion != null) {
                    WebDriverManager.chromedriver()
                        .driverVersion(chromeDriverVersion)
                        .avoidBrowserDetection()
                        .setup();
                } else {
                    WebDriverManager.chromedriver().setup(); // auto-resolve for local
                }

                ChromeOptions options = new ChromeOptions();

                options.addArguments("--disable-blink-features=AutomationControlled",
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-gpu",
                    "--remote-debugging-pipe",
                    "--disable-extensions",
                    "--disable-software-rasterizer"/*, "incognito"*/);

                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                                         "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");

                options.setCapability("goog:loggingPrefs", Map.of("browser", "ALL", "performance", "ALL"));

                if (headless.equalsIgnoreCase("true")) {
                    options.addArguments("--headless",
                        "--window-size=1920,1050");     // Avoid space in argument value);
                }

                //options.merge(sslError);
                System.setProperty("webdriver.chrome.verboseLogging", "true");
                System.setProperty("webdriver.chrome.logfile", "chromedriver.log");

                try {
                    driver = new ChromeDriver(options);
                    ((JavascriptExecutor) driver).executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
                } catch (Exception e) {
                    log.error("Failed to initialize ChromeDriver", e);
                    driver = null;
                }
            } else if(browser.contains("firefox") || browser.contains("ff")) {
                String firefoxVersion = System.getProperty("FIREFOX_VERSION",
                    System.getenv().getOrDefault("FIREFOX_VERSION", "139.0.4"));

                if (isRunningInDocker() && firefoxVersion != null) {
                    WebDriverManager.firefoxdriver()
                        .driverVersion(firefoxVersion)
                        .avoidBrowserDetection()
                        .setup();
                } else {
                    WebDriverManager.firefoxdriver().setup(); // auto-resolve for local
                }

                FirefoxOptions options = new FirefoxOptions();
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("dom.webdriver.enabled", false);
                profile.setPreference("useAutomationExtension", false);

                // Optional: Spoof user agent
                profile.setPreference("general.useragent.override",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:124.0) Gecko/20100101 Firefox/124.0");

                // Disable tracking protection (can cause issues during test load)
                profile.setPreference("privacy.trackingprotection.enabled", false);

                options.setProfile(profile);
                options.addArguments("-private");
                if (headless.equalsIgnoreCase("true")) {
                    options.addArguments("-width 1920", "-height 1080", "-headless");
                }
                options.addArguments("--no-sandbox"); // run sandboxed or not
                //options.merge(sslError);
                driver = new FirefoxDriver(options);
            } else if(System.getProperty("os.name").indexOf("win")==0 && (browser.contains("iexplore") || browser.contains("internet"))) {
                WebDriverManager.iedriver().setup();
                InternetExplorerOptions options = new InternetExplorerOptions();
                //options.merge(sslError);
                driver = new InternetExplorerDriver(options);
            }
        }

        if (driver == null) {
            throw new IllegalStateException("WebDriver instance could not be initialized for browser: " + browser);
        }

        return driver;
    }
}
