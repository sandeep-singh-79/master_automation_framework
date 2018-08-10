package ai.leverton.demo.driver;

import ai.leverton.demo.exception.NoSuchDriverException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class RemoteDriver extends Driver {
    public RemoteDriver() { super(); }

    @Override
    public WebDriver createDriver() throws NoSuchDriverException {
        String browser = config.getProperty("BROWSER");
        String hostAddress = config.getProperty("remote.ip");
        int hostPort = Integer.parseInt(config.getProperty("remote.port"));
        Platform platform = Platform.valueOf(config.getProperty("remote.platform"));
        String version = config.getProperty("remote.version");
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
                        new URL("http://"+hostAddress+":"+hostPort+"/wd/hub"), capabilities));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return driver;
    }
}
