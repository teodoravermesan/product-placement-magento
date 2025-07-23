package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CheckoutPage {
    private WebDriver driver;
    private WebDriverWait wait;

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
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        PageFactory.initElements(driver, this);
    }

    public void fillShippingAddress(String email, String firstName, String lastName, String street,
                                    String city, String stateValue, String zip,
                                    String countryValue, String phone) {
        wait.until(ExpectedConditions.visibilityOf(firstNameInput));
        emailInput.clear();
        emailInput.sendKeys(email);

        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);

        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);

        streetInput.clear();
        streetInput.sendKeys(street);

        cityInput.clear();
        cityInput.sendKeys(city);

        Select stateSelect = new Select(stateDropdown);
        stateSelect.selectByValue(stateValue);

        zipInput.clear();
        zipInput.sendKeys(zip);

        Select countrySelect = new Select(countryDropdown);
        countrySelect.selectByValue(countryValue);

        phoneInput.clear();
        phoneInput.sendKeys(phone);
    }

    public void continueShipping() {
        wait.until(ExpectedConditions.elementToBeClickable(shippingContinueButton));
        shippingContinueButton.click();
        wait.until(ExpectedConditions.invisibilityOf(shippingContinueButton));
    }

    public void selectShippingMethod(String visibleText) {
        wait.until(ExpectedConditions.visibilityOfAllElements(shippingMethodRows));
        for (WebElement row : shippingMethodRows) {
            if (row.getText().toLowerCase().contains(visibleText.toLowerCase())) {
                row.click();
                break;
            }
        }
    }

    public void placeOrder() {
        wait.until(ExpectedConditions.invisibilityOf(loadingMask));
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton));
        placeOrderButton.click();
    }
}
