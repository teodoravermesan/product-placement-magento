package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderConfirmationPage extends BasePage {

    @FindBy(css = "div.page-title-wrapper span.base")
    private WebElement confirmationMessage;

    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String getOrderSuccessMessage() {
        return confirmationMessage.getText();
    }
}