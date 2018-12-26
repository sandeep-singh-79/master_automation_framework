package com.sandeep.driver;

import com.sandeep.config.FrameworkConfig;
import com.sandeep.exception.NoSuchDriverException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;

import java.util.Properties;

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