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

    @BeforeMethod
    public void initPages() {
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        loginPage = new LoginPage(driver);
        checkoutPage = new CheckoutPage(driver);
        orderConfirmationPage = new OrderConfirmationPage(driver);
        showCartPage = new ShowCartPage(driver);
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
    public void addProductToCart() {
        homePage.searchProduct(TestData.PRODUCT_NAME);
        homePage.selectFirstProduct();
        AdHelper.cleanGoogleVignetteFragment(driver);
        AdHelper.closeGoogleVignetteAdIfPresent(driver);
        homePage.selectFirstProduct();
        productPage.selectSize(TestData.SIZE);
        productPage.selectColor(TestData.COLOR);
        productPage.setQuantity(1);
        productPage.addToCart();
    }

    @Test(priority = 4)
    public void proceedToCheckoutFromCart() {
        productPage.waitForAddToCartSuccess();
        productPage.openCart();
        showCartPage.proceedToCheckout();
        checkoutPage.selectShippingMethod(TestData.SHIPPING_METHOD);
        checkoutPage.continueShipping();
    }

    @Test(priority = 5)
    public void placeOrder() {
        checkoutPage.placeOrder();
        String actualMessage = orderConfirmationPage.getOrderSuccess();
        Assert.assertEquals(actualMessage, TestData.SUCCES_ORDER_MESSAGE);
    }
}
