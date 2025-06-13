package com.sandeep.driver;

import com.sandeep.config.FrameworkConfig;
import com.sandeep.exception.NoSuchDriverException;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public final class WebDriverFactory implements Serializable, Cloneable {
    private static volatile WebDriverFactory instance;
    private static ThreadLocal<WebDriver> driver;
    private final Properties config;

    // webdriver instantiation properties
    private final String browser;

    /*
     * thanks to grasshopper7 for the below code on how to close all webdriver instances for parallel execution of
     * cucumber-jvm scenarios
     * grasshopper7 - https://github.com/grasshopper7/testngcuke4sharedwebdriver
     */
    //To quit the drivers and browsers at the end only.
    private static final List<WebDriver> storedDrivers = new ArrayList<>();

    // adding a shutdown hook to close all browsers once the execution ends
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> storedDrivers.forEach(WebDriver::quit)));
    }

    public void addDriver (WebDriver driver) {
        storedDrivers.add(driver);
    }

    public void closeDriver () {
        WebDriver wd = driver.get();
        if (wd != null) {
            wd.quit();
            driver.remove();             // Remove from ThreadLocal after quitting
            storedDrivers.remove(wd);    // Clean up mapping
        }
    }

    private WebDriverFactory() {
        config = FrameworkConfig.getInstance().getConfigProperties();
        browser = System.getProperty( "browser", config.getProperty("BROWSER"));
    }

    public static WebDriverFactory getInstance() {
        if(instance == null) {
            synchronized (WebDriverFactory.class) {
                if(instance == null){
                    instance = new WebDriverFactory();
                }
            }
        }

        return instance;
    }

    public WebDriver getDriver(String driverType) throws NullPointerException {
        driver = ThreadLocal.withInitial(() -> {
            WebDriver tempDriver = null;
            try {
                switch (driverType.toLowerCase()) {
                    case "local": {
                        tempDriver = getLocalDriverInstance();
                        break;
                    }
                    case "remote": {
                        tempDriver = getRemoteDriverInstance();
                        break;
                    }
                    default:
                        throw new NoSuchDriverException(String.format("UnSupported driver type requested: %s", driverType));
                }
            } catch (NoSuchDriverException e) {
                e.printStackTrace();
            }

            return tempDriver;
        });

        return driver.get();
    }

    private WebDriver getLocalDriverInstance() {
        /*if (localDriverInstance == null) {
            synchronized (WebDriverFactory.class) {
                if (localDriverInstance == null)
                    localDriverInstance = new LocalDriver(browser).createDriver();
            }
        }*/
        String headless = System.getProperty("headless", config.getProperty("headless"));
        return new LocalDriver(browser, headless).createDriver();
    }

    private WebDriver getRemoteDriverInstance() throws NoSuchDriverException {
        String serverAddress = config.getProperty(System.getProperty("host"), "remote.ip");
        int serverPort = Integer.parseInt(config.getProperty(System.getProperty("port"), "remote.port"));
        String version = config.getProperty(System.getProperty("version"), "remote.version");
        Platform platform = Platform.fromString(config.getProperty(System.getProperty("platform"), "remote.platform")) ;

        /*if (remoteDriverInstance == null) {
            synchronized (WebDriverFactory.class) {
                if (remoteDriverInstance == null)
                    remoteDriverInstance = new RemoteDriver(browser, serverAddress, serverPort, platform, version)
                            .createDriver();
            }
        }*/

        return new RemoteDriver(browser, serverAddress, serverPort, platform, version)
                .createDriver();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        log.info("Not allowed to clone the current class");
        log.info("throwing CloneNotSupportedException for your pains ...");
        throw new CloneNotSupportedException(String.format("Cloning not allowed for %s class", WebDriverFactory.class));
    }

    public Object readResolve() {
        return instance;
    }
}
