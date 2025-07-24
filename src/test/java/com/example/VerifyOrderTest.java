package com.example;

import base.BaseTest;
import com.beust.ah.A;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;
import utils.AdHelper;
import utils.CookieConsentHandler;
import utils.TestData;

import java.util.List;

@Epic("Regression Tests")
@Feature("Order Placement")
@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class VerifyOrderTest extends BaseTest {
    private HomePage homePage;
    private LoginPage loginPage;

    MyAccountPage accountPage = new MyAccountPage(driver);
    MyOrdersPage ordersPage = new MyOrdersPage(driver);

    @BeforeMethod
    public void initPages() {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        accountPage = new MyAccountPage(driver);
        ordersPage = new MyOrdersPage(driver);
    }

    @Test(priority = 1)
    public void openHomePage() {
        loadHomePage();
        AdHelper.cleanGoogleVignetteFragment(driver);
        AdHelper.closeGoogleVignetteAdIfPresent(driver);
    }

    @Test(priority = 2)
    public void login() {
        homePage.clickSignIn();
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);
    }

    @Test(priority = 3)
    public void navigateToMyAccount() {
        loginPage.goToMyAccount();
        AdHelper.cleanGoogleVignetteFragment(driver);
        AdHelper.closeGoogleVignetteAdIfPresent(driver);
        homePage.clickSignIn();
    }

    @Test(priority = 4)
    public void navigateToMyOrders() {
        accountPage.goToMyOrders();
    }

    @Test(priority = 5)
    public void verifyFirstOrderInHistory() {
        List<WebElement> orders = ordersPage.getOrderRows();
        WebElement firstOrder = orders.get(0);

        String orderNumber = ordersPage.getOrderNumber(firstOrder);
        String orderDate = ordersPage.getOrderDate(firstOrder);
        String shipTo = ordersPage.getShipTo(firstOrder);
        String orderTotal = ordersPage.getOrderTotal(firstOrder);
        String orderStatus = ordersPage.getOrderStatus(firstOrder);

        System.out.println("Order Number: " + orderNumber);
        System.out.println("Order Date: " + orderDate);
        System.out.println("Ship To: " + shipTo);
        System.out.println("Order Total: " + orderTotal);
        System.out.println("Order Status: " + orderStatus);

        Assert.assertFalse(orderDate.isEmpty());
        Assert.assertFalse(shipTo.isEmpty());
        Assert.assertTrue(orderTotal.startsWith("$"));
        Assert.assertFalse(orderStatus.isEmpty());

        ordersPage.clickViewOrder(firstOrder);
        Assert.assertTrue(driver.getCurrentUrl().contains(orderNumber) || driver.getTitle().contains(orderNumber));
    }

}
