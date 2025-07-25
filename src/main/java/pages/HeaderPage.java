package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HeaderPage extends BasePage {

    @FindBy(css = "button.action.switch")
    private WebElement accountDropdown;

    @FindBy(linkText = "Sign Out")
    private WebElement signOutLink;

    @FindBy(linkText = "My Account")
    private WebElement myAccount;

    @FindBy(linkText = "Sign In")
    private WebElement signInLink;

    public HeaderPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickSignIn() {
        waitForElementToBeClickable(signInLink);
        signInLink.click();
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

    public boolean isSignInVisible() {
        return signInLink.isDisplayed();
    }

}