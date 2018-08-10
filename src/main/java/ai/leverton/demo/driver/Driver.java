package ai.leverton.demo.driver;

import ai.leverton.demo.config.PropertyReader;
import ai.leverton.demo.exception.NoSuchDriverException;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.Properties;

abstract class Driver {
    protected WebDriver driver;
    protected Properties config;

    public Driver() {
        this(new File(System.getProperty("user.dir")+"/src/main/resources/frameworkConfig.properties"));
    }

    public Driver(File propertiesFile) {
        config = new PropertyReader(propertiesFile).getPropertyFile();
    }

    public abstract WebDriver createDriver() throws NoSuchDriverException;

}
