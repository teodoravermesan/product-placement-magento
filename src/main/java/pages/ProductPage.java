package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By sizeOption(String size) {
        return By.xpath("//div[@option-label='" + size + "']");
    }

    private By colorOption(String color) {
        return By.xpath("//div[@option-label='" + color + "']");
    }

    @FindBy(id = "qty")
    private WebElement quantityInput;

    @FindBy(id = "product-addtocart-button")
    private WebElement addToCartButton;

    @FindBy(css = "div.message-success")
    private WebElement cartSuccessMessage;

    @FindBy(css = "a.action.showcart")
    private WebElement miniCart;

    @FindBy(css = "div.message-success.success.message[data-ui-id='message-success']")
    private WebElement successMessage;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void selectSize(String size) {
        wait.until(ExpectedConditions.elementToBeClickable(sizeOption(size))).click();
    }

    public void selectColor(String color) {
        wait.until(ExpectedConditions.elementToBeClickable(colorOption(color))).click();
    }

    public void setQuantity(int qty) {
        wait.until(ExpectedConditions.visibilityOf(quantityInput));
        quantityInput.clear();
        quantityInput.sendKeys(String.valueOf(qty));
    }

    public void addToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    }

    public String waitForAddToCartSuccess() {
        wait.until(ExpectedConditions.visibilityOf(successMessage));
        return successMessage.getText();
    }

    public void openCart() {
        miniCart.click();
    }
}
