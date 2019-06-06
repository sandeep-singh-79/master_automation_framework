package com.sandeep.util;

import com.sandeep.base.BasePageObject;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.*;
import java.util.stream.Collectors;

public class WebPageAction {
    private final BasePageObject basePageObject;
    private WebDriver driver;
    private WebDriverWait wait;
    private Wait <WebDriver> fluentWait;
    private Actions mouseOver;
    private JavascriptExecutor js;

    public WebPageAction (BasePageObject basePageObject, WebDriver driver) {
        this.basePageObject = basePageObject;

        this.driver = driver;
        wait = new WebDriverWait(driver, 50);

        fluentWait = new FluentWait <>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(200))
                .ignoreAll(Arrays.asList(NoSuchElementException.class, StaleElementReferenceException.class));
        mouseOver = new Actions(driver);
        js = (JavascriptExecutor) driver;
    }

    public void waitForElementToBeDisplayed (WebElement element) {
        fluentWait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToBeDisplayed (By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForElementToBePresent (By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void waitForElementToBeNotPresent (By locator) {
        wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(locator)));
    }

    public void waitForElementsToBePresent (By locator) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public void waitForElementTobeClickable (WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementsToBeDisplayed (List <WebElement> elements) {
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    public void waitForElementToBeInvisible (By elementLocator) {
        fluentWait.until(ExpectedConditions.invisibilityOfElementLocated(elementLocator));
    }

    public void wait_for_element_to_not_be_present (By locator) {
        fluentWait.until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(locator)));
    }

    public void waitForTextToBeInvisible (By locator, String text) {
        wait.until(ExpectedConditions.invisibilityOfElementWithText(locator, text));
    }

    public void waitForElementToBeDisplay (String className) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.className(className)));
    }/// <summary>

    /// this function will refreshes the current web page
    /// </summary>
    public void refreshPage () {
        driver.navigate().refresh();
    }/// <summary>

    /// this function will perform move back a single "item" in the web browser's history
    /// </summary>
    public void navigateBack () {
        driver.navigate().back();
    }/// <summary>

    /// this function will perform move a single "item" forward in the web browser's history
    /// </summary>
    public void navigateForward () {
        driver.navigate().forward();
    }/// <summary>

    /// this function will close the working window
    /// </summary>
    public void closeWindow () {
        driver.close();
    }/// <summary>

    /// When a webelement don't have unique selector to locate in that case first we will find the element in main DOM
    /// and then click on the element by using tagname
    /// </summary>
    public void clickOnElementUsingTagName (By by, String tagName) throws Exception {
        WebElement tempEle = driver.findElement(by);
        WebElement elementToCLick = tempEle.findElement(By.tagName(tagName));
        waitForElementTobeClickable(elementToCLick);
        Thread.sleep(2000);
        elementToCLick.click();
    }/// <summary>

    /// this function will convert the RGB color value to hexadecimal value.
    /// </summary>
    /// <param locator="webElement">webElement to locate on application</param>
    ///<param propertyName="name">name of of the css property</param>
    public String convertRGBColorToHexadecimal (String css, String propertyName) {
        String color = findElementByLocator(By.cssSelector(css)).getCssValue(propertyName);
        String[] hexValue = color.replace("rgba(", "").replace(")", "").split(",");
        int hexValue1 = Integer.parseInt(hexValue[0]);
        hexValue[1] = hexValue[1].trim();
        int hexValue2 = Integer.parseInt(hexValue[1]);
        hexValue[2] = hexValue[2].trim();
        int hexValue3 = Integer.parseInt(hexValue[2]);
        String actualColor = String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3);
        return actualColor;
    }/// <summary>

    /// this function is to used to select the particular element using index vale from the list
    /// </summary>
    /// <param element="webElement">list of web-elements</param>
    /// <param elementIndex="elementToSelect">index of the element on which we have perform click</param>
    public void SelectElementFromListByIndexPosition (List <WebElement> element, int elementIndex) {
        try {
            if (element != null) {
                element.get(elementIndex).click();
            }
        } catch (NullPointerException e) {
            System.out.println("the element list has not been initialized!!!");
            e.printStackTrace();
        } catch (IndexOutOfBoundsException iobe) {
            System.out.println(String.format("The list size - %d, is less than the expected index %d", element.size(), elementIndex));
            iobe.printStackTrace();
        }
    }/// <summary>

    /// This method will select a particular element from a list.
    /// </summary>
    /// <param list="listOfWebElement">consist set of webElement returned by locator</param>
    /// <param elementName="nameOFElement">name of element to be selected</param>
    public void selectElementFromListByString (List <WebElement> list, String elementName) {
        list.stream().filter(webElement -> getTextField(webElement).equals(elementName)).findFirst().ifPresent(WebElement::click);
    }/// <summary>

    /// This method will search a particular element from a list.
    /// </summary>
    /// <param list="listOfWebElement">consist set of webElement returned by locator</param>
    /// <param elementName="nameOFElement">name of element to be selected</param>
    public boolean searchElementFromListByString (List <WebElement> list, String elementName) {
        boolean is_element_present = list.stream().anyMatch(webElement -> getTextField(webElement).equals(elementName));
        return is_element_present;
    }/// <summary>

    /// this function set a value to text field
    /// </summary>
    /// <param element="webElement">text field on which we have to set the text</param>
    public void setTextField (WebElement element, String value) {
        element.click();
        element.clear();
        element.sendKeys(value);
    }

    public void waitForSec () throws Exception {
        Thread.sleep(1000);
    }/// <summary>

    /// this function get text value from field
    /// </summary>
    /// <param element="webElement">text field on which we have to get the text</param>
    public String getTextField (WebElement element) {
        waitForElementToBeDisplayed(element);
        return element.getText();
    }/// <summary>

    /// this function get the attribute name of the element
    /// </summary>
    /// <param element="webElement">element field on which we have to get the attribute name</param>
    public String getAttribute (WebElement element, String attributeName) {
        waitForElementToBeDisplayed(element);
        return element.getAttribute(attributeName);
    }/// <summary>

    /// this function get the color attribute name of the element
    /// </summary>
    /// <param element="webElement">element field whose color we have to get</param>
    public String getColour (WebElement element) {
        waitForElementToBeDisplayed(element);
        return element.getCssValue("color");
    }/// <summary>

    /// this function get the font of the element
    /// </summary>
    /// <param element="webElement">element field whose font we have to get</param>
    public String getFontType (WebElement element) {
        waitForElementToBeDisplayed(element);
        return element.getCssValue("font-weight");
    }/// <summary>

    /// this function get the background color of the element
    /// </summary>
    /// <param element="webElement">element field whose font we have to get</param>
    public String getBackgroundColor (WebElement element) {
        waitForElementToBeDisplayed(element);
        return element.getCssValue("background-color");
    }

    public String getBackgroundColor (By by) {
        waitForElementToBeDisplayed(by);
        return driver.findElement(by).getCssValue("background-color");
    }/// <summary>

    /// this function return the text of alert
    /// </summary>
    /// <param alert="typeOfAlert">alert</param>
    public String getAlertText (Alert alert) {
        return alert.getText();
    }/// <summary>

    /// this function return a string list that a collection consist
    /// </summary>
    /// <param textCollection="webElement">webElement that return a collection of elements</param>
    public List <String> getTextCollection (List <WebElement> textCollection) {
        return textCollection.stream().map(WebElement::getText).collect(Collectors.toList());
    }/// <summary>

    /// this function perform a click using action class
    /// </summary>
    /// <param element="webElement">webElement on which we have to perform click</param>
    public void clickelementViaAction (WebElement element) {
        Actions clicker = new Actions(driver);
        clicker.moveToElement(element).perform();
        clicker.click().perform();
    }/// <summary>

    /// this function return the text of button
    /// </summary>
    /// <param element="button">webElement of button</param>
    public String GetButtonText (By by) {
        return driver.findElement(by).getAttribute("value");
    }/// <summary>

    /// sometime selenium waits are not sufficient enough in that we will use hard wait.
    /// </summary>
    public void longPause () {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
    }/// <summary>

    ///
    /// </summary>
    public void pauseForTime (long timeInMilliSec) {
        try {
            Thread.sleep(timeInMilliSec);
        } catch (InterruptedException e) {
        }
    }

    public void mediumPause () {
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
        }
    }/// <summary>

    /// sometime selenium waits are not sufficient enough in that we will use hard wait.
    /// </summary>
    public void shortPause () {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
    }/// <summary>

    /// sometime selenium waits are not sufficient enough in that we will use hard wait.
    /// </summary>
    public void veryShortPause () {
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
        }
    }/// <summary>

    /// this function will copy the content from text file in to the text field.
    /// </summary>
    /// <param locator="webElement">webElement(text field on which we have to copy the content) to locate on application</param>
    public void CopyContentFromFile (WebElement element, String filePath) {
        String data;
        int counter = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while ((data = reader.readLine()) != null) {
                element.sendKeys(data);
                counter++;
            }
        } catch (IOException e) {
        }
    }/// <summary>

    /// this function will convert the RGB color value to hexadecimal value.
    /// </summary>
    /// <param locator="webElement">webElement to locate on application</param>
    ///<param propertyName="name">name of of the css property</param>
    public String convertRGBColorToHexadecimal (WebElement element, String propertyName) {
        String color = element.getCssValue(propertyName);
        String[] hexValue = color.replace("rgba(", "").replace(")", "").split(",");
        int hexValue1 = Integer.parseInt(hexValue[0]);
        hexValue[1] = hexValue[1].trim();
        int hexValue2 = Integer.parseInt(hexValue[1]);
        hexValue[2] = hexValue[2].trim();
        int hexValue3 = Integer.parseInt(hexValue[2]);
        return String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3);
    }/// <summary>

    /// this function will delete the file from downloads folder if found
    /// </summary>
    public void verifyAndDeleteFile (String downloads, String fileName) {
        File path = new File(downloads + fileName);
        if (path.exists()) {
            path.delete();
            System.out.println("File has been deleted");
        } else {
            System.out.println("File not present");
        }
    }/// <summary>

    /// this function will verify if the file exists at a path
    /// </summary>
    public boolean checkFileInFolder (String downloads, String fileName) {
        boolean flag = false;
        File path = new File(downloads + fileName);
        if (path.exists()) {
            return true;
        }
        return flag;
    }/// <summary>

    /// this function will upload a file to the application
    /// </summary>
    public void fileUpload (String relativePath) throws Exception {
        Runtime.getRuntime().exec(new File(relativePath).getAbsolutePath());
    }/// <summary>

    /// this function will verify if text present is read only
    /// </summary>
    public boolean verifyReadOnlyText (WebElement ele) {
        boolean flag = false;
        waitForElementToBeDisplayed(ele);
        String tempText = getTextField(ele);
        try {
            ele.sendKeys("...");
        } catch (WebDriverException ignored) {
        }

        if (tempText.equals(getTextField(ele))) {
            return true;
        }
        return flag;
    }/// <summary>

    /// this function will return the system date in the form of DD/MM/YYYY HR:MIN:SEC
    /// </summary>
    public String getDate () {
        int day, month, year;
        int second, minute, hour;
        GregorianCalendar date = new GregorianCalendar();

        day = date.get(Calendar.DAY_OF_MONTH);
        month = date.get(Calendar.MONTH);
        year = date.get(Calendar.YEAR);

        second = date.get(Calendar.SECOND);
        minute = date.get(Calendar.MINUTE);
        hour = date.get(Calendar.HOUR);

        return " " + day + "/" + (month + 1) + "/" + year + " " + hour + " : " + minute + " : " + second;
    }/// <summary>

    /// this function will wait for an element to become clickable and then click it
    /// </summary>
    public void click (WebElement element) {
        waitForElementTobeClickable(element);
        element.click();
    }/// <summary>

    /// this function will return a Webelement when found by a By locator
    /// </summary>
    public WebElement findElementByLocator (By by) {
        return driver.findElement(by);
    }/// <summary>

    /// this function will return a list of Webelements when found by a By locator
    /// </summary>
    public List <WebElement> findElements (By by) {
        return driver.findElements(by);
    }/// <summary>

    /// this function will upload the file.
    /// </summary>
    /// <param fileToBeUploaded="fileName">file to be uploaded from resources folder</param>
    public void uploadFile (String fileToBeUploaded) throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        //System.out.println("Printing name of file to verify correct file has been picked: " +fileToBeUploaded);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(fileToBeUploaded);
        //System.out.println("stringSelection="+ stringSelection.toString());
        clipboard.setContents(stringSelection, null);
        //System.out.println("clipboard selection=" + clipboard.getContents(stringSelection));
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }/// <summary>

    /// this method is used to select the particular option from the list
    /// </summary>
    /// <param actionToPerform="nameOfTheOption">on which option we want to select from the list</param>
    /// <param webelements="listOfWebelements">list of web-elements from which we have to select the option</param>
    public void selectoptionFromExistingSurveyPopUp (String actionToPerform, List <WebElement> webelements) {
        List <WebElement> optionList = webelements;
        for (WebElement webElement : optionList) {
            if (webElement.getText().equals(actionToPerform)) {
                shortPause();
                webElement.click();
                break;
            }
        }
    }/// <summary>

    /// this function return a Split part of the String  with the particular expression
    /// </summary>
    /// <param stringToSplit is the string which needs to be splitted and Expression is by which split needs to be performed</param>
    public List <String> splitStringIntoList (String stringToSplit, String expression) {
        List <String> list = new ArrayList <String>();
        String[] parts = stringToSplit.split(expression);
        String part1 = parts[0];
        String part2 = parts[1];
        String part3 = parts[2];
        list.add(part1);
        list.add(part2);
        list.add(part3);
        return list;
    }/// <summary>

    /// this function will perform double click on web-element.
    /// </summary>
    /// <param webelement="elementOnWhichAction">list of web-elements from which we have to select the option</param>
    public void doubleClick (WebElement element) {
        Actions action = new Actions(driver).doubleClick(element);
        action.build().perform();
    }/// <summary>

    /// this function will perform selecting element from dropdown list.
    /// </summary>
    public void selectFromDropDownList (WebElement element, String byVisibleText) {
        Select select = new Select(element);
        select.selectByVisibleText(byVisibleText);
    }/// <summary>

    /// this function will perform selecting element from dropdown list.
    /// </summary>
    public String getSelectedElementFromDropDownList (WebElement element) {
        Select select = new Select(element);
        return select.getFirstSelectedOption().getText();
    }/// <summary>

    /// this function will perform selecting element from dropdown list by index
    /// </summary>
    public void selectFromDropDownListByIndex (WebElement element, int indexValue) {
        Select select = new Select(element);
        select.selectByIndex(indexValue);
    }// <summary>

    /// this function will perform click on web-element using java script.
    /// </summary>
    /// <param webelement="elementOnWhichAction">list of web-elements from which we have to select the option</param>
    public void clickUsingJavaScript (String elementToCLick) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript(elementToCLick);
    }/// <summary>

    /// this function will return true if element is visible or displayed else retrun false
    /// </summary>
    public boolean isElementVisible (WebElement element) {
        return (element.isDisplayed());
    }/// <summary>

    /// this function will return true if element is visible or displayed else retrun false
    /// </summary>
    public void checkElementVisible (WebElement element) {
        element.isDisplayed();
    }

    public void click_on_element_with_js (WebElement element_to_click) {
        waitForElementTobeClickable(element_to_click);
        mouseOver.moveToElement(element_to_click).build().perform();
        js.executeScript("arguments[0].click()", element_to_click);
    }

    public void enter_value_js (String locatorType, String locator, String value_to_enter) {
        switch (locatorType.toLowerCase()) {
            case "id":
                js.executeScript(String.format("document.getElementById('%s').value=%s", locator, value_to_enter));
                break;
            case "name":
                js.executeScript(String.format("document.getElementsByName('%s').value=%s", locator, value_to_enter));
                break;
            case "classname":
                js.executeScript(String.format("document.getElementsByClassName('%s').value=%s", locator, value_to_enter));
                break;
        }

    }

    public void upload_file (By locator, String file_name) {
        driver.findElement(locator)
                .sendKeys(String.format("%s/src/test/resources/test_data/%s"
                        , System.getProperty("user.dir"), file_name));
        waitForElementToBeInvisible(By.cssSelector("div.loading-spinner.loading"));
    }

    public String getTextAt (String element_id) {
        if (driver instanceof JavascriptExecutor) {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            return (String) javascriptExecutor.executeScript(String.format("return document.getElementById('%s').value;", element_id));
        }
        return null;
    }

    /*/// <summary>
	/// this function will wait for alert to be present if its is expected
	/// </summary>
	public Alert waitForAlertTobePresent(){
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert=driver.switchTo().alert();
		return alert;
	}

	/// <summary>
	/// this function will wait for element to disappear from UI when it is located in DOM
	/// </summary>
	public void waitForElementToDisappear(By by){
		fluentWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}


	/// <summary>
	/// this function will wait for Web Element to get disappear from UI when it is located in DOM
	/// </summary>
	public void waitForWebElementToGetDisappear(WebElement element){
		fluentWait.until(ExpectedConditions.stalenessOf(element));
	}*/
}