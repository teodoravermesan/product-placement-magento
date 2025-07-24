package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class CheckoutPage extends BasePage {
    @FindBy(id = "customer-email")
    private WebElement emailInput;

    @FindBy(name = "firstname")
    private WebElement firstNameInput;

    @FindBy(name = "lastname")
    private WebElement lastNameInput;

    @FindBy(name = "street[0]")
    private WebElement streetInput;

    @FindBy(name = "city")
    private WebElement cityInput;

    @FindBy(name = "region_id")
    private WebElement stateDropdown;

    @FindBy(name = "postcode")
    private WebElement zipInput;

    @FindBy(name = "country_id")
    private WebElement countryDropdown;

    @FindBy(name = "telephone")
    private WebElement phoneInput;

    @FindBy(css = "button.button.action.continue.primary")
    private WebElement shippingContinueButton;

    @FindBy(css = "table.table-checkout-shipping-method tbody tr")
    private List<WebElement> shippingMethodRows;

    @FindBy(css = "button.action.primary.checkout")
    private WebElement placeOrderButton;

    @FindBy(css = ".loading-mask, .modal-popup")
    private WebElement loadingMask;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void fillShippingAddress(String email, String firstName, String lastName, String street,
                                    String city, String stateValue, String zip,
                                    String countryValue, String phone) {
        waitForElementToBeVisible(firstNameInput);
        emailInput.sendKeys(email);
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        streetInput.sendKeys(street);
        cityInput.sendKeys(city);
        Select stateSelect = new Select(stateDropdown);
        stateSelect.selectByValue(stateValue);
        zipInput.sendKeys(zip);
        Select countrySelect = new Select(countryDropdown);
        countrySelect.selectByValue(countryValue);
        phoneInput.sendKeys(phone);
    }

    public void continueShipping() {
        waitForElementToBeClickable(shippingContinueButton);
        shippingContinueButton.click();
    }

    public void selectShippingMethod(String visibleText) {
        waitForAllElementsToBeVisible(shippingMethodRows);
        for (WebElement row : shippingMethodRows) {
            if (row.getText().toLowerCase().contains(visibleText.toLowerCase())) {
                row.click();
                break;
            }
        }
    }

    public void placeOrder() {
        waitForElementToBeInvisible(loadingMask);
        waitForElementToBeClickable(placeOrderButton);
        placeOrderButton.click();
        waitForElementToBeInvisible(loadingMask);
    }
}