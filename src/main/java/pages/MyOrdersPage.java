package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MyOrdersPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "table.data.table tbody tr")
    private List<WebElement> orderRows;

    public MyOrdersPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public List<WebElement> getOrderRows() {
        return orderRows;
    }

    // Example method to get order number from a row element
    public String getOrderNumber(WebElement row) {
        return row.findElement(By.cssSelector("td.col.id")).getText().trim();
    }

    public String getOrderDate(WebElement row) {
        return row.findElement(By.cssSelector("td.col.date")).getText().trim();
    }

    public String getShipTo(WebElement row) {
        return row.findElement(By.cssSelector("td.col.shipping")).getText().trim();
    }

    public String getOrderTotal(WebElement row) {
        return row.findElement(By.cssSelector("td.col.total span.price")).getText().trim();
    }

    public String getOrderStatus(WebElement row) {
        return row.findElement(By.cssSelector("td.col.status")).getText().trim();
    }

    public void clickViewOrder(WebElement row) {
        row.findElement(By.cssSelector("td.col.actions a.action.view")).click();
    }

}
