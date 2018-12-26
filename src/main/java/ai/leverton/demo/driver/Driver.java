package ai.leverton.demo.driver;

import ai.leverton.demo.config.FrameworkConfig;
import ai.leverton.demo.exception.NoSuchDriverException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

abstract class Driver {
    protected WebDriver driver;
    protected String browser = null;
    protected Platform platform = null;
    protected String version = null;
    protected String serverAddress = null;
    protected int serverPort = 4444;

    protected Properties config;

    Driver() {
        config = FrameworkConfig.getInstance().getConfigProperties();
    }

    abstract WebDriver createDriver() throws NoSuchDriverException;
}