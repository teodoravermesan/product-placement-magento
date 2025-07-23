package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class CookieConsentHandler {

    public static void acceptConsent(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        WebElement consentBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//p[@class='fc-button-label' and normalize-space(text())='Consent']/parent::button")
        ));
        consentBtn.click();
    }
}
