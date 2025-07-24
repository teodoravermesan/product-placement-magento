package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage extends BasePage {
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

    @FindBy(css = "[data-ui-id='message-success']")
    private WebElement successMessage;

    @FindBy(css = ".loading-mask")
    private WebElement loadingMask;

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void selectSize(String size) {
        waitForElement(sizeOption(size)).click();
    }


    public void selectColor(String color) {
        waitForElement(colorOption(color)).click();
    }

    public void setQuantity(int qty) {
        waitForElementToBeVisible(quantityInput);
        quantityInput.clear();
        quantityInput.sendKeys(String.valueOf(qty));
    }

    public void addToCart() {
        waitForElementToBeClickable(addToCartButton);
        addToCartButton.click();
    }

    public String getAddToCartSuccess() {
        waitForElementToBeVisible(successMessage);
        return successMessage.getText();
    }

    public void waitForAddToCartSuccessMessage() {
        waitForElementToBeVisible(successMessage);
    }

    public void openCart() {
        miniCart.click();
    }
}