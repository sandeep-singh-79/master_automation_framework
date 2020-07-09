package com.sandeep.util;

import com.sandeep.base.BasePageObject;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import static org.apache.commons.io.FileUtils.copyFile;
import static org.testng.Assert.fail;

@Slf4j
public class Utils {
    public static String getDate () {
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        Date dateobj = new Date();
        return (df.format(dateobj));
    }

    public static String getTimeStamp () {
        return (getTimeStamp("dd-MMM-yyyy HH:mm:ss").replaceAll(" ", ""));
    }

    public static String getTimeStamp (String dateFormat) {
        DateFormat df = new SimpleDateFormat(dateFormat);
        Date dateobj = new Date();
        return (df.format(dateobj));
    }

    public static File take_screenshot (WebDriver driver, ITestResult testResult) throws NullPointerException {
        return take_screenshot(driver, testResult.getMethod().getMethodName());
    }

    public static File take_screenshot (WebDriver driver, final String method_name) {
        if (driver == null) return null;

        File screenshotFile = null;
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            screenshotFile = create_screenshot_file(method_name,
                    createDirectory(String.format("%s/Screenshots/%s", System.getProperty("user.dir"), getDate())));
            copyFile(screenshot.getScreenshotAs(OutputType.FILE), screenshotFile);
        } catch (Exception ioe) {
            log.error("Encountered issue while creating screenshot for method/scenario {}", method_name);
            log.error("This was caused by {}", ioe.getCause());
            log.error(String.valueOf(ioe.getStackTrace()));
        }

        return screenshotFile;
    }

    public static <T extends BasePageObject> T get_instance (Class <T> clazz, WebDriver driver) {
        T instance = null;
        if (clazz != null) {
            try {
                instance = clazz.getConstructor(WebDriver.class).newInstance(driver);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                log.error("Found an issue while creating instance of {}", clazz.getSimpleName());
                log.error(e.getMessage());
                log.error(Arrays.toString(e.getStackTrace()));
            }
        }
        return instance;
    }

    public static void log_exception (Exception exception) {
        log.error("Exception encountered during execution! Please see message: {}", exception.getMessage());
        log.error(Arrays.toString(exception.getStackTrace()));
    }

    public static void log_exception (String message, Exception exception) {
        log.error(message);
        log.error(Arrays.toString(exception.getStackTrace()));
    }

    public static void log_exception_and_fail (Exception exception) {
        log_exception(exception);
        fail();
    }

    public static void log_exception_and_fail (String message, Exception exception) {
        log_exception(message, exception);
        fail();
    }

    public static int get_random_index (final int min, final int max) {
        return (new Random().nextInt(max - min + 1) + min) - 1;
    }

    private static File create_screenshot_file (final String method_name, final File screenshot_dir) {
        return new File(String.format("%s/%s_%s.png", screenshot_dir.getPath(), method_name, Utils.getTimeStamp("dd-MM-yyyy_HH_mm_ss")));
    }

    public static File createDirectory (String directoryPath) {
        File screenshotDir = new File(directoryPath);
        if (!screenshotDir.exists()) screenshotDir.mkdirs();
        return screenshotDir;
    }
}
