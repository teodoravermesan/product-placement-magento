package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected WebDriver driver;
    private WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    protected void waitForElementToBeVisible(WebElement element) {
        try {
            if (element != null) {
                wait.until(ExpectedConditions.visibilityOf(element));
            } else {
                throw new NoSuchElementException("Element passed to waitForElementToBeVisible is null.");
            }
        } catch (TimeoutException | NoSuchElementException e) {
            System.err.println("[ERROR] Element not visible or null: " + e.getMessage());
        }
    }
    protected void waitForElementToBeClickable(WebElement element) {
        try {
            if (element != null) {
                wait.until(ExpectedConditions.elementToBeClickable(element));
            } else {
                throw new NoSuchElementException("Element passed to waitForElementToBeClickable is null.");
            }
        } catch (TimeoutException | NoSuchElementException e) {
            System.err.println("[ERROR] Element not clickable or null: " + e.getMessage());
        }
    }

    protected void waitForElementToBeInvisible(WebElement element) {
        try {
            if (element != null) {
                wait.until(ExpectedConditions.invisibilityOf(element));
            } else {
                throw new NoSuchElementException("Element passed to waitForElementToBeInvisible is null.");
            }
        } catch (TimeoutException | NoSuchElementException e) {
            System.err.println("[ERROR] Element not invisible or null: " + e.getMessage());
        }
    }

    protected void waitForAllElementsToBeVisible(List<WebElement> webElements) {
        try {
            for (WebElement element : webElements) {
                wait.until(ExpectedConditions.visibilityOfAllElements(element));
            }
        } catch (TimeoutException timeoutException) {

        } catch (NullPointerException nullPointerException) {

        }
    }

    public WebElement waitForElement(By locator) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            System.out.println("Element not clickable: " + locator);
            return null;
        }
    }
}