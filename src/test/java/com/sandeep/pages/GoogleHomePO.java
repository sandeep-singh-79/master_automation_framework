package com.sandeep.pages;

import com.sandeep.base.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GoogleHomePO extends BasePageObject {
    @FindBy(css = "textarea[name='q']")
    private WebElement searchTextBox;

    @FindBy(css = "input[name='btnK']")
    private WebElement searchButton;

    @FindBy(css = "img[alt='Google']")
    private WebElement googleImage;

    public GoogleHomePO (WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);
    }

    public GoogleSearchPO performSearch (String search) {
        searchTextBox.clear();
        searchTextBox.sendKeys(search);
        if (is_page_ajax_loaded())
            searchTextBox.sendKeys(Keys.ENTER);

        return new GoogleSearchPO(driver);
    }

    /**
     * Each page object must implement this method to return the identifier of a unique WebElement on that page.
     * The presence of this unique element will be used to assert that the expected page has finished loading
     *
     * @return the By locator of unique element(s) on the page
     */
    @Override
    protected By getUniqueElement () {
        return By.xpath("//div[@aria-label='Search by voice']");
    }
}
