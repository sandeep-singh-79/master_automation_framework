package com.sandeep.pages;

import com.sandeep.base.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class GoogleSearchPO extends BasePageObject {
    @FindBy(css = "div.rc h3")
    private List <WebElement> searchResult;

    public int searchResultCount () {
        return searchResult.size() + 1;
    }

    public GoogleSearchPO (WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);
    }

    /**
     * Each page object must implement this method to return the identifier of a unique WebElement on that page.
     * The presence of this unique element will be used to assert that the expected page has finished loading
     *
     * @return the By locator of unique element(s) on the page
     */
    @Override
    protected By getUniqueElement () {
        return By.cssSelector("div.rc h3");
    }
}
