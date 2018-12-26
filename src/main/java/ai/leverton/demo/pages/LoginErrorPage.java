package ai.leverton.demo.pages;

import ai.leverton.demo.base.BasePageObject;
import ai.leverton.demo.config.FrameworkConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Properties;

import static org.openqa.selenium.support.PageFactory.initElements;

public class LoginErrorPage extends BasePageObject {
    @FindBy(className = "title")
    private WebElement errorTitle;
    @FindBy(className = "subtitle")
    private WebElement errorTxt;

    public LoginErrorPage(WebDriver driver) {
        this(driver, FrameworkConfig.getInstance().getConfigProperties());
    }

    public LoginErrorPage(WebDriver driver, Properties config) {
        super(driver, config);
        initElements(ajaxElementLocatorFactory, this);
    }

    @Override
    protected By getUniqueElement() {
        return By.xpath("//*[@class='title']");
    }

    public String getErrorText() {
        return errorTxt.getText();
    }
}