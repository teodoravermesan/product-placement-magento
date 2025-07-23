package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(linkText = "Sign In")
    private WebElement signInLink;

    @FindBy(id = "search")
    private WebElement searchInput;

    @FindBy(css = "button.action.search")
    private WebElement searchButton;

    @FindBy(css = "li.product-item a.product-item-link")
    private WebElement firstSearchResult;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    public void loadHomePage() {
        String url = ConfigReader.get("base.url");
        driver.get(url);
    }

    public void clickSignIn() {
        wait.until(ExpectedConditions.elementToBeClickable(signInLink)).click();
    }

    public void searchProduct(String productName) {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.sendKeys(productName);
        searchButton.click();
    }

    public void selectFirstProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(firstSearchResult)).click();
    }

    public boolean isSignInVisible() {
        return signInLink.isDisplayed();
    }
}
