package com.sandeep.driver;

import com.sandeep.exception.NoSuchDriverException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

class RemoteDriver extends Driver {
    RemoteDriver() {
        super();
    }

    RemoteDriver(String browser, String serverAddress, int serverPort, Platform platform, String version) {
        this();

        this.browser = browser;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.platform = platform;
        this.version = version;
    }

    @Override
    public WebDriver createDriver() throws NoSuchDriverException {
        DesiredCapabilities capabilities;

        if(driver == null) {
            if (browser.equals("firefox")) {
                capabilities = DesiredCapabilities.firefox();
            } else if (browser.equals("internetExplorer") || browser.contains("internet") || browser.contains("iexplore")) {
                capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            } else if (browser.equals("chrome")) {
                capabilities = DesiredCapabilities.chrome();
            } else {
                throw new NoSuchDriverException("Browser type unsupported");
            }

            capabilities.setVersion(version);
            capabilities.setPlatform(platform);
            try {
                driver = (new RemoteWebDriver(
                        new URL("http://"+serverAddress+":"+serverPort+"/wd/hub"), capabilities));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return driver;
    }
}
