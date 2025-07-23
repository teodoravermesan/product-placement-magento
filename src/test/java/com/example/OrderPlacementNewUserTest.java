package com.example;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import utils.AdHelper;
import utils.CookieConsentHandler;

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
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
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

    @Test(priority = 3)
    public void proceedToCheckoutFromCart() {
        productPage.waitForAddToCartSuccess();
        productPage.openCart();
        showCartPage.proceedToCheckout();
        checkoutPage.fillShippingAddress("testuser@example.com",
                "Teo", "Test", "Nicholas Escobar 23t", "New City",
                "43", "123456", "US", "1234567890"
        );
        checkoutPage.selectShippingMethod("Flat Rate");
        checkoutPage.continueShipping();
    }

    @Test(priority = 4)
    public void placeOrder() {
        checkoutPage.placeOrder();
        Assert.assertTrue(orderConfirmationPage.isOrderSuccess(), "Order was not successful!");
    }
}
