package ai.leverton.demo.pages;

import ai.leverton.demo.base.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePageObject {
    By txtUsername = By.id("username");
    By txtPassword = By.name("j_password");
    By loginBtn = By.xpath("//*[@type='submit' and @value='Log In']");

    private WebElement usernameTxt;
    private WebElement passwordTxt;
    private WebElement btnLogin;

    public LoginPage(WebDriver driver) {
        super(driver);

        try {
            usernameTxt = driver.findElement(txtUsername);
            passwordTxt = driver.findElement(txtPassword);
            btnLogin = driver.findElement(loginBtn);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected By getUniqueElement() {
        return By.id("username");
    }

    public LoginErrorPage attemptSignIn(String username, String password) {
        enterText(usernameTxt, username);
        enterText(passwordTxt, password);
        btnLogin.click();

        return new LoginErrorPage(driver);
    }
}
