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
    private LoginPage loginPage;
    private HeaderPage headerPage;
    private MyAccountPage accountPage;
    private MyOrdersPage ordersPage;


    @BeforeMethod
    public void initPages() {
        logger.info("Initializing page objects for VerifyOrderTest");
        loginPage = new LoginPage(driver);
        accountPage = new MyAccountPage(driver);
        ordersPage = new MyOrdersPage(driver);
        headerPage = new HeaderPage(driver);
        accountPage = new MyAccountPage(driver);
        ordersPage = new MyOrdersPage(driver);
        headerPage = new HeaderPage(driver);
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
        headerPage.clickSignIn();
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);
        Assert.assertTrue(loginPage.getWelcomeMessage().contains(TestData.WELCOME_MESSAGE));
    }

    @Test(priority = 3)
    public void navigateToMyAccount() {
        logger.info("Navigating to My Account page");
        headerPage.goToMyAccount();
        AdHelper.cleanGoogleVignetteFragment(driver);
        AdHelper.closeGoogleVignetteAdIfPresent(driver);
        headerPage.clickSignIn();
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
        String orderNumber = ordersPage.getOrderNumber(0);
        String orderDate = ordersPage.getOrderDate(0);
        String shipTo = ordersPage.getShipTo(0);
        String orderTotal = ordersPage.getOrderTotal(0);
        String orderStatus = ordersPage.getOrderStatus(0);

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
        ordersPage.clickViewOrder(0);
        String currentUrl = driver.getCurrentUrl();
        String pageTitle = driver.getTitle();
        logger.info("Current URL after clicking view order: {}", currentUrl);
        logger.info("Page title after clicking view order: {}", pageTitle);
        Assert.assertTrue(driver.getCurrentUrl().contains(orderNumber) || driver.getTitle().contains(orderNumber));
    }

}
