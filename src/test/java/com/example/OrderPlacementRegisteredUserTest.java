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
import utils.CookieConsentHandler;

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
        homePage.loadHomePage();
        CookieConsentHandler.acceptConsent(driver);
    }

    @Test(priority = 2)
    public void login() {
        homePage.clickSignIn();
        loginPage.login("testuser@example.com", "Test@1234");
    }

    @Test(priority = 3)
    public void addProductToCart() {
        homePage.searchProduct("Breathe-Easy Tank");
        homePage.selectFirstProduct();
        AdHelper.cleanGoogleVignetteFragment(driver);
        AdHelper.closeGoogleVignetteAdIfPresent(driver);
        homePage.selectFirstProduct();
        productPage.selectSize("M");
        productPage.selectColor("Purple");
        productPage.setQuantity(1);
        productPage.addToCart();
    }

    @Test(priority = 4)
    public void proceedToCheckoutFromCart() {
        productPage.waitForAddToCartSuccess();
        productPage.openCart();
        showCartPage.proceedToCheckout();
        checkoutPage.selectShippingMethod("Flat Rate");
        checkoutPage.continueShipping();
    }

    @Test(priority = 5)
    public void placeOrder() {
        checkoutPage.placeOrder();
        Assert.assertTrue(orderConfirmationPage.isOrderSuccess(), "Order was not successful!");
    }
}
