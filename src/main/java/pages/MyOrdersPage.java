package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MyOrdersPage  extends BasePage{

    @FindBy(id = "my-orders-table")
    private WebElement ordersTable;

    public MyOrdersPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public List<WebElement> getOrderRows() {
        return ordersTable.findElements(By.xpath(".//tbody/tr"));
    }

    public String getOrderNumber(int rowIndex) {
        WebElement row = getOrderRows().get(rowIndex);
        return row.findElement(By.cssSelector("td.col.id")).getText().trim();
    }

    public String getOrderDate(int rowIndex) {
        WebElement row = getOrderRows().get(rowIndex);
        return row.findElement(By.cssSelector("td.col.date")).getText().trim();
    }

    public String getShipTo(int rowIndex) {
        WebElement row = getOrderRows().get(rowIndex);
        return row.findElement(By.cssSelector("td.col.shipping")).getText().trim();
    }

    public String getOrderTotal(int rowIndex) {
        WebElement row = getOrderRows().get(rowIndex);
        return row.findElement(By.cssSelector("td.col.total span.price")).getText().trim();
    }

    public String getOrderStatus(int rowIndex) {
        WebElement row = getOrderRows().get(rowIndex);
        return row.findElement(By.cssSelector("td.col.status")).getText().trim();
    }

    public void clickViewOrder(int rowIndex) {
        WebElement row = getOrderRows().get(rowIndex);
        row.findElement(By.cssSelector("td.col.actions a.action.view")).click();
    }
}