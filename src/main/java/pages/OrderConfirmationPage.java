package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderConfirmationPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "div.checkout-success")
    private WebElement successMessage;

    public OrderConfirmationPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public boolean isOrderSuccess() {
        wait.until(ExpectedConditions.visibilityOf(successMessage));
        return successMessage.isDisplayed();
    }
}
