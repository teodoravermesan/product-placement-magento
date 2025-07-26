package com.example;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;
import data.TestData;

@Epic("Regression Tests")
@Feature("Order Placement")
@Listeners({utils.ExtentTestListener.class, io.qameta.allure.testng.AllureTestNg.class})
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
    }

    @Test(priority = 1)
    public void openHomePage() {
        logger.info("Home page is loading");
        loadHomePage();
    }

    @Test(priority = 2, dependsOnMethods = {"openHomePage"})
    public void login() {
        logger.info("Logging in user: {}", TestData.VALID_USERNAME);
        headerPage.clickSignIn();
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);
        Assert.assertTrue(loginPage.getWelcomeMessage().contains(TestData.WELCOME_MESSAGE));
    }

    @Test(priority = 3, dependsOnMethods = {"login"})
    public void navigateToMyAccount() {
        logger.info("Navigating to My Account page");
        headerPage.goToMyAccount();
        logger.info("Successfully navigated to My Account");
    }


    @Test(priority = 4, dependsOnMethods = {"navigateToMyAccount"})
    public void navigateToMyOrders() {
        logger.info("Navigating to My Orders page");
        accountPage.goToMyOrders();
    }

    @DataProvider(name = "orderIndices")
    public Object[][] orderIndices() {
        return new Object[][] { {0} };
    }

    @Test(priority = 5, dependsOnMethods = {"navigateToMyOrders"}, dataProvider = "orderIndices")
    public void verifyFirstOrderInHistory(int rowIndex) {
        try {
            logger.info("Checking if order history has at least one order");
            Assert.assertTrue(ordersPage.getOrderCount() > 0, "No orders found in order history");

            logger.info("Verifying order at row index: {}", rowIndex);
            String orderNumber = ordersPage.getOrderNumber(rowIndex);
            String orderDate = ordersPage.getOrderDate(rowIndex);
            String shipTo = ordersPage.getShipTo(rowIndex);
            String orderTotal = ordersPage.getOrderTotal(rowIndex);
            String orderStatus = ordersPage.getOrderStatus(rowIndex);

            logger.info("Order Number: {}", orderNumber);
            logger.info("Order Date: {}", orderDate);
            logger.info("Ship To: {}", shipTo);
            logger.info("Order Total: {}", orderTotal);
            logger.info("Order Status:{}", orderStatus);

            Assert.assertFalse(orderDate.isEmpty(), "Order date should not be empty");
            Assert.assertFalse(shipTo.isEmpty(), "Ship To should not be empty");
            Assert.assertTrue(orderTotal.startsWith("$"), "Order total should start with $");
            Assert.assertFalse(orderStatus.isEmpty(), "Order status should not be empty");
            logger.info("Clicking view order link for order number: {}", orderNumber);
            ordersPage.clickViewOrder(rowIndex);
            String currentUrl = driver.getCurrentUrl();
            String pageTitle = driver.getTitle();
            logger.info("Current URL after clicking view order: {}", currentUrl);
            logger.info("Page title after clicking view order: {}", pageTitle);
            Assert.assertTrue(currentUrl.contains(orderNumber) || pageTitle.contains(orderNumber),
                    "Order number should be present in URL or page title");
        } catch (IndexOutOfBoundsException e) {
            logger.warn("Order at index {} does not exist. Skipping this test.", rowIndex);
            throw new SkipException("Order index " + rowIndex + " out of bounds");
        }
    }

}
