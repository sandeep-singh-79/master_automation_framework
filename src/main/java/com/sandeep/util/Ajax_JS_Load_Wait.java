package com.sandeep.util;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Slf4j
public class Ajax_JS_Load_Wait {
    private WebDriver driver;
    private WebDriverWait wait;

    public Ajax_JS_Load_Wait (WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public static ExpectedCondition <Boolean> completion_of_javascript = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
    public static ExpectedCondition <Boolean> completion_of_jquery = driver -> (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active==0");
    public static ExpectedCondition <Boolean> completion_of_angular = driver -> (Boolean) ((JavascriptExecutor) driver).executeScript("return angular.element(document.body).injector().get(\\'$http\\').pendingRequests.length== 0");

    public boolean is_page_js_loaded () {
        // wait till the javascript running in the background has run completely
        try {
            wait.pollingEvery(Duration.ofMillis(200)).ignoring(StaleElementReferenceException.class, TimeoutException.class)
                    .until(completion_of_javascript);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean is_page_jquery_loaded () {
        // wait till the javascript running in the background has run completely
        try {
            wait.pollingEvery(Duration.ofMillis(200)).ignoring(StaleElementReferenceException.class, TimeoutException.class)
                    .until(completion_of_jquery);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean is_page_angular_loaded () {
        // wait till the javascript running in the background has run completely
        try {
            wait.pollingEvery(Duration.ofMillis(200)).ignoring(StaleElementReferenceException.class, TimeoutException.class)
                    .until(completion_of_angular);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean wait_for_ajax_to_finish () {
        // wait till the javascript running in the background has run completely
        return (is_page_js_loaded() || is_page_jquery_loaded() || is_page_angular_loaded());
    }

}
