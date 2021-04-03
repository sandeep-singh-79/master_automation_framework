package com.sandeep.driver;

import com.sandeep.exception.NoSuchDriverException;
import org.openqa.selenium.WebDriver;

interface IDriver {
    WebDriver createDriver() throws NoSuchDriverException;
}