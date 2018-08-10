package ai.leverton.demo.pages;

import ai.leverton.demo.base.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginErrorPage extends BasePageObject {
    private WebElement errorTitle;
    private WebElement errorTxt;

    public LoginErrorPage(WebDriver driver) {
        super(driver);

        errorTitle = driver.findElement(By.className("title"));
        errorTxt = driver.findElement(By.className("subtitle"));
    }

    @Override
    protected By getUniqueElement() {
        return By.xpath("//*[@class='title']");
    }

    public String getErrorText() {
        return errorTxt.getText();
    }
}
