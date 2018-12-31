package com.mckinsey.cet.util;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.commons.io.FileUtils.copyFile;

@Slf4j
public class Utils {
    public static String getDate() {
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        Date dateobj = new Date();
        return (df.format(dateobj));
    }

    public static String getTimeStamp() {
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date dateobj = new Date();
        return (df.format(dateobj).replaceAll(" ", ""));
    }

    public static File take_screenshot(WebDriver driver, ITestResult testResult) throws NullPointerException {
        if (driver == null) return null;
        if (driver != null) {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File screenshotFile = create_screenshot_File(testResult,
                    createDirectory(String.format("%s/Screenshots/%s", System.getProperty("user.dir"), getDate())));
            try {
                copyFile(screenshot.getScreenshotAs(OutputType.FILE), screenshotFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return screenshotFile;
        }

        return null;
    }

    private static File create_screenshot_File(ITestResult testResult, File screenshotDir) {
        return new File(String.format("%s/%s%s.png", screenshotDir.getPath(), testResult.getMethod().getMethodName(), Utils.getTimeStamp()));
    }

    private static File createDirectory(String directoryPath) {
        File screenshotDir = new File(directoryPath);
        if(!screenshotDir.exists()) screenshotDir.mkdirs();
        return screenshotDir;
    }
}
