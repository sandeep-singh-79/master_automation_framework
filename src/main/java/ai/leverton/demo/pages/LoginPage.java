package ai.leverton.demo.pages;

import ai.leverton.demo.base.BasePageObject;
import ai.leverton.demo.config.FrameworkConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Properties;

import static org.openqa.selenium.support.PageFactory.initElements;

public class LoginPage extends BasePageObject {
    @FindBy(id = "username")
    private WebElement usernameTxt;
    @FindBy(name = "j_password")
    private WebElement passwordTxt;
    @FindBy(css = "input.secondary-btn.btn")
    private WebElement btnLogin;

    public LoginPage(WebDriver driver) {
        this(driver, FrameworkConfig.getInstance().getConfigProperties());
    }

    public LoginPage(WebDriver driver, Properties config) {
        super(driver, config);
        initElements(ajaxElementLocatorFactory, this);
    }

    @Override
    protected By getUniqueElement() {
        return By.className("forgot-link");
    }

    public LoginErrorPage attemptSignIn(String username, String password) {
        enterText(usernameTxt, username);
        enterText(passwordTxt, password);
        btnLogin.click();

        return new LoginErrorPage(driver);
    }
}