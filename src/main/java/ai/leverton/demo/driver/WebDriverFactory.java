package ai.leverton.demo.driver;

import ai.leverton.demo.exception.NoSuchDriverException;
import org.openqa.selenium.WebDriver;

public final class WebDriverFactory {
    private WebDriver driver;

    public WebDriver getDriver(String driverType) throws NoSuchDriverException {
        switch (driverType) {
            case "local": {
                driver = new LocalDriver().createDriver();
                return driver;
            }
            case "remote": {
                driver = new RemoteDriver().createDriver();
                return driver;
            }
            default:
                throw new NoSuchDriverException("UnSupported driver type requested: " + driverType);
        }
    }

    public void closeDriver() {
        if(driver != null) {
            driver.close();
            driver.quit();
        }
    }
}
