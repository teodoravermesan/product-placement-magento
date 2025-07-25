package com.example;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;
import utils.AdHelper;
import utils.TestData;

@Epic("Regression Tests")
@Feature("Order Placement")
@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class OrderPlacementRegisteredUserTest extends BaseTest {
    private HomePage homePage;
    private ProductPage productPage;
    private LoginPage loginPage;
    private CheckoutPage checkoutPage;
    private OrderConfirmationPage orderConfirmationPage;
    private ShowCartPage showCartPage;
    private HeaderPage headerPage;

    @BeforeMethod
    public void initPages() {
        logger.info("Initializing page objects for new test.");
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        loginPage = new LoginPage(driver);
        checkoutPage = new CheckoutPage(driver);
        orderConfirmationPage = new OrderConfirmationPage(driver);
        showCartPage = new ShowCartPage(driver);
        headerPage = new HeaderPage(driver);
    }

    @Test(priority = 1)
    public void openHomePage() {
        logger.info("Opening home page.");
        loadHomePage();
        AdHelper.cleanGoogleVignetteFragment(driver);
        AdHelper.closeGoogleVignetteAdIfPresent(driver);
    }

    @Test(priority = 2)
    public void login() {
        logger.info("Starting login with registered user.");
        headerPage.clickSignIn();
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);
        Assert.assertTrue(loginPage.getWelcomeMessage().contains(TestData.WELCOME_MESSAGE));
        logger.info("Login successful.");
    }

    @Test(priority = 3)
    public void addProductToCart() {
        logger.info("Searching for product: {}", TestData.PRODUCT_NAME);
        homePage.searchProduct(TestData.PRODUCT_NAME);
        logger.info("Selecting first product from search results.");
        homePage.selectFirstProduct();
        AdHelper.cleanGoogleVignetteFragment(driver);
        AdHelper.closeGoogleVignetteAdIfPresent(driver);
        homePage.selectFirstProduct();
        logger.info("Customizing product with size '{}' and color '{}'", TestData.SIZE, TestData.COLOR);
        productPage.selectSize(TestData.SIZE);
        productPage.selectColor(TestData.COLOR);
        productPage.setQuantity(1);
        logger.info("Adding product to cart.");
        productPage.addToCart();
        logger.info("Waiting for add to cart success message.");
        productPage.waitForAddToCartSuccessMessage();
        Assert.assertTrue(productPage.getAddToCartSuccessMessage().contains(TestData.SUCCES_ADD_TO_CART_MESSAGE + TestData.PRODUCT_NAME + TestData.SUCCES_ADD_TO_CART_MESSAGE1));
    }

    @Test(priority = 4)
    public void proceedToCheckoutFromCart() {
        logger.info("Opening cart.");
        productPage.openCart();
        logger.info("Proceeding to checkout.");
        showCartPage.proceedToCheckout();
        logger.info("Selecting shipping method: {}", TestData.SHIPPING_METHOD);
        checkoutPage.selectShippingMethod(TestData.SHIPPING_METHOD);
        logger.info("Clicking next button on checkout.");
        checkoutPage.clickOnNextButton();
    }

    @Test(priority = 5)
    public void placeOrder() {
        logger.info("Placing the order.");
        checkoutPage.placeOrder();
        String actualMessage = orderConfirmationPage.getOrderSuccessMessage();
        logger.info("Order confirmation message: '{}'", actualMessage);
        Assert.assertEquals(actualMessage, TestData.SUCCES_ORDER_MESSAGE);
    }
}
