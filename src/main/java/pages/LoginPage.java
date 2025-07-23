package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "pass")
    private WebElement passwordInput;

    @FindBy(id = "send2")
    private WebElement signInButton;

    @FindBy(css = "span.logged-in")
    private WebElement welcomeMessage;

    @FindBy(css = "div.message-error")
    private WebElement errorMessage;

    @FindBy(css = "button.action.switch")
    private WebElement accountDropdown;

    @FindBy(linkText = "Sign Out")
    private WebElement signOutLink;

    @FindBy(linkText = "My Account")
    private WebElement myAccount;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.clear();
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        signInButton.click();
    }

    public String getWelcomeMessage() {
        wait.until(ExpectedConditions.visibilityOf(welcomeMessage));
        return welcomeMessage.getText();
    }

    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
        return errorMessage.getText();
    }

    public void signOut() {
        wait.until(ExpectedConditions.visibilityOf(accountDropdown));
        accountDropdown.click();
        signOutLink.click();
    }

    public void goToMyAccount() {
        wait.until(ExpectedConditions.visibilityOf(accountDropdown));
        accountDropdown.click();
        myAccount.click();

    }
}
