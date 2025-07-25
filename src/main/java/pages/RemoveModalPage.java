package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RemoveModalPage extends BasePage {

    @FindBy(css = ".modal-popup.confirm._show")
    private WebElement confirmRemoveModal;

    @FindBy(css = ".modal-popup.confirm._show .action-primary.action-accept")
    private WebElement confirmRemoveOKButton;

    @FindBy(css = ".modal-popup.confirm._show .modal-content")
    private WebElement confirmModalContent;

    public RemoveModalPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isRemoveModalDisplayed() {
        return confirmRemoveModal.isDisplayed();
    }

    public void clickOkRemoveModal() {
        waitForElementToBeClickable(confirmRemoveOKButton);
        confirmRemoveOKButton.click();
    }

    public String getRemoveModalContent() {
        waitForElementToBeVisible(confirmModalContent);
        return confirmModalContent.getText();
    }

}