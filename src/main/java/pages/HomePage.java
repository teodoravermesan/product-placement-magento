package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {
    @FindBy(linkText = "Sign In")
    private WebElement signInLink;

    @FindBy(id = "search")
    private WebElement searchInput;

    @FindBy(css = "button.action.search")
    private WebElement searchButton;

    @FindBy(css = "li.product-item a.product-item-link")
    private WebElement firstSearchResult;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickSignIn() {
        waitForElementToBeClickable(signInLink);
        signInLink.click();
    }

    public void searchProduct(String productName) {
        waitForElementToBeVisible(searchInput);
        searchInput.sendKeys(productName);
        searchButton.click();
    }

    public void selectFirstProduct() {
        waitForElementToBeClickable(firstSearchResult);
        firstSearchResult.click();
    }

    public boolean isSignInVisible() {
        return signInLink.isDisplayed();
    }
}