package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ShowCartPage  extends BasePage {
    @FindBy(id = "top-cart-btn-checkout")
    private WebElement proceedToCheckoutButton;

    @FindBy(css = ".cart.item .product-item-name")
    private WebElement cartItemName;

    @FindBy(css = "a.action.delete")
    private WebElement removeItemButton;

    @FindBy(css = ".action-primary.action-accept")
    private WebElement confirmRemoveButton;

    @FindBy(css = "strong.subtitle.empty")
    private WebElement emptyCartMessage;

    @FindBy(css = "aside.modal-popup.confirm._show")
    private WebElement confirmModal;

    @FindBy(css = ".modal-content[data-role='content'] > div")
    private WebElement confirmModalContent;

    @FindBy(css = "aside.modal-popup.confirm._show footer.modal-footer button.action-primary.action-accept[data-role='action']")
    private WebElement confirmOkButton;

    @FindBy(xpath = "//ol[@id='mini-cart']//li[contains(@class, 'product-item')]//strong[@class='product-item-name']//a")
    private List<WebElement> productNameLinks;

    @FindBy(css = ".loading-mask, .modal-popup")
    private WebElement loadingMask;

    public ShowCartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void proceedToCheckout() {
        proceedToCheckoutButton.click();
        waitForElementToBeInvisible(loadingMask);
    }

    public boolean isProductInCartByName(String productName) {
        for (WebElement productLink : productNameLinks) {
            if (productLink.getText().trim().equals(productName)) {
                return true;
            }
        }
        return false;
    }

    public void removeItemFromCart() {
        waitForElementToBeVisible(removeItemButton);
        removeItemButton.click();
    }

    public void clickOkOnConfirmationModal() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(confirmOkButton)).click();
    }

    public boolean isCartEmpty() {
        waitForElementToBeVisible(emptyCartMessage);
        return emptyCartMessage.isDisplayed();
    }
}