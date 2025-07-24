package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage  extends BasePage{

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
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void login(String email, String password) {
        waitForElementToBeVisible(emailInput);
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        signInButton.click();
    }

    public String getWelcomeMessage() {
        waitForElementToBeVisible(welcomeMessage);
        return welcomeMessage.getText();
    }

    public String getErrorMessage() {
        waitForElementToBeVisible(errorMessage);
        return errorMessage.getText();
    }

    public void signOut() {
        waitForElementToBeVisible(accountDropdown);
        accountDropdown.click();
        signOutLink.click();
    }

    public void goToMyAccount() {
        waitForElementToBeClickable(accountDropdown);
        accountDropdown.click();
        myAccount.click();
    }


}