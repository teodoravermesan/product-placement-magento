package com.example;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import utils.AdHelper;
import utils.TestData;

@Epic("Regression Tests")
@Feature("Order Placement")
@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class OrderPlacementNewUserTest extends BaseTest {
    private HomePage homePage;
    private ProductPage productPage;
    private CheckoutPage checkoutPage;
    private OrderConfirmationPage orderConfirmationPage;
    private ShowCartPage showCartPage;

    @BeforeMethod
    public void initPages() {
        logger.info("Initializing page objects for a new test.");
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        checkoutPage = new CheckoutPage(driver);
        orderConfirmationPage = new OrderConfirmationPage(driver);
        showCartPage = new ShowCartPage(driver);
    }

    @Test(priority = 1)
    public void openHomePage() {
        logger.info("Opening home page.");
        loadHomePage();
        AdHelper.cleanGoogleVignetteFragment(driver);
        AdHelper.closeGoogleVignetteAdIfPresent(driver);
        logger.info("Home page opened and ads handled.");
    }

    @Test(priority = 2)
    public void searchProduct() {
        logger.info("Searching product: {}", TestData.PRODUCT_NAME);
        homePage.searchProduct(TestData.PRODUCT_NAME);
    }

    @Test(priority = 3)
    public void selectFirstProduct() {
        logger.info("Selecting first product from search results.");
        homePage.selectFirstProduct();
        AdHelper.cleanGoogleVignetteFragment(driver);
        AdHelper.closeGoogleVignetteAdIfPresent(driver);
        homePage.selectFirstProduct();
    }

    @Test(priority = 4)
    public void customizeProduct() {
        logger.info("Customizing product with size '{}' and color '{}'", TestData.SIZE, TestData.COLOR);
        productPage.selectSize(TestData.SIZE);
        productPage.selectColor(TestData.COLOR);
        productPage.setQuantity(1);
    }

    @Test(priority = 5)
    public void addProductToCart() {
        logger.info("Adding product to cart.");
        productPage.addToCart();
        logger.info("Waiting for add-to-cart success message.");
        productPage.waitForAddToCartSuccessMessage();
        Assert.assertTrue(productPage.getAddToCartSuccessMessage().contains(TestData.SUCCES_ADD_TO_CART_MESSAGE + TestData.PRODUCT_NAME + TestData.SUCCES_ADD_TO_CART_MESSAGE1));

    }

    @Test(priority = 6)
    public void openCart() {
        logger.info("Opening cart.");
        productPage.openCart();
    }

    @Test(priority = 7)
    public void proceedToCheckoutFromCart() {
        logger.info("Proceeding to checkout.");
        showCartPage.proceedToCheckout();
        logger.info("Filling shipping address.");
        checkoutPage.fillShippingAddress(TestData.VALID_USERNAME,
                TestData.FIRST_NAME, TestData.LAST_NAME, TestData.STREET, TestData.CITY, TestData.STATE, TestData.ZIP, TestData.COUNTRY, TestData.PHONE);
        logger.info("Selecting shipping method: {}", TestData.SHIPPING_METHOD);
        checkoutPage.selectShippingMethod(TestData.SHIPPING_METHOD);
        logger.info("Clicking next button on checkout.");
        checkoutPage.clickOnNextButton();
    }

    @Test(priority = 8)
    public void placeOrder() {
        logger.info("Placing order.");
        checkoutPage.placeOrder();
        String actualMessage = orderConfirmationPage.getOrderSuccessMessage();
        logger.info("Order confirmation message received: '{}'", actualMessage);
        Assert.assertEquals(actualMessage, TestData.SUCCES_ORDER_MESSAGE);
    }
}
