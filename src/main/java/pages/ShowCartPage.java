package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ShowCartPage  extends BasePage {
    @FindBy(id = "top-cart-btn-checkout")
    private WebElement proceedToCheckoutButton;

    @FindBy(css = "a[title='Remove item']")
    private WebElement removeItemButton;

    @FindBy(css = ".subtitle.empty")
    private WebElement emptyCartMessage;

    @FindBy(css = ".product-item-details .product-item-name a")
    private List<WebElement> productNameLinks;

    @FindBy(css = ".loading-mask")
    private WebElement loadingMask;

    public ShowCartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void proceedToCheckout() {
        proceedToCheckoutButton.click();
        waitForElementToBeInvisible(loadingMask);
    }

    public boolean isItemInCart(String productName) {
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

    public boolean isCartEmpty() {
        waitForElementToBeVisible(emptyCartMessage);
        return emptyCartMessage.isDisplayed();
    }
}