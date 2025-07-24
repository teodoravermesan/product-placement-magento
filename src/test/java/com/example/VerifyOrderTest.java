package com.example;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;
import utils.AdHelper;
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
        logger.info("Initializing page objects for VerifyOrderTest");
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        accountPage = new MyAccountPage(driver);
        ordersPage = new MyOrdersPage(driver);
    }

    @Test(priority = 1)
    public void openHomePage() {
        logger.info("Loading home page");
        loadHomePage();
        AdHelper.cleanGoogleVignetteFragment(driver);
        AdHelper.closeGoogleVignetteAdIfPresent(driver);
    }

    @Test(priority = 2)
    public void login() {
        logger.info("Logging in user: {}", TestData.VALID_USERNAME);
        homePage.clickSignIn();
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);
        Assert.assertTrue(loginPage.getWelcomeMessage().contains(TestData.WELCOME_MESSAGE));
    }

    @Test(priority = 3)
    public void navigateToMyAccount() {
        logger.info("Navigating to My Account page");
        loginPage.goToMyAccount();
        AdHelper.cleanGoogleVignetteFragment(driver);
        AdHelper.closeGoogleVignetteAdIfPresent(driver);
        homePage.clickSignIn();
        logger.info("Successfully navigated to My Account");
    }

    @Test(priority = 4)
    public void navigateToMyOrders() {
        logger.info("Navigating to My Orders page");
        accountPage.goToMyOrders();
    }

    @Test(priority = 5)
    public void verifyFirstOrderInHistory() {
        logger.info("Retrieving order rows");
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
        logger.info("Clicking view order link for order number: {}", orderNumber);
        ordersPage.clickViewOrder(firstOrder);
        String currentUrl = driver.getCurrentUrl();
        String pageTitle = driver.getTitle();
        logger.info("Current URL after clicking view order: {}", currentUrl);
        logger.info("Page title after clicking view order: {}", pageTitle);
        Assert.assertTrue(driver.getCurrentUrl().contains(orderNumber) || driver.getTitle().contains(orderNumber));
    }

}
