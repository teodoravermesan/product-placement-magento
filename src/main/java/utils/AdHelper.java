package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AdHelper {

    public static void cleanGoogleVignetteFragment(WebDriver driver) {
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("#google_vignette")) {
            String cleanUrl = currentUrl.replace("#google_vignette", "");
            driver.get(cleanUrl);
        }
    }

    public static void closeGoogleVignetteAdIfPresent(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("div[aria-label='Close'], button[aria-label='Close'], .google_vignette")
            ));
            closeBtn.click();
        } catch (Exception e) {
        }
    }
}
