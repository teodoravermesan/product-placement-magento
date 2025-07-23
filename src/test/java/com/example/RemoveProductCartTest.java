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
public class RemoveProductCartTest extends BaseTest {
    private HomePage homePage;
    private ProductPage productPage;
    private LoginPage loginPage;
    private ShowCartPage showCartPage;

    @BeforeMethod
    public void initPages() {
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        loginPage = new LoginPage(driver);
        showCartPage = new ShowCartPage(driver);
    }

    @Test(priority = 1)
    public void openHomePage() {
        homePage.loadHomePage();
        CookieConsentHandler.acceptConsent(driver);
        AdHelper.cleanGoogleVignetteFragment(driver);
        AdHelper.closeGoogleVignetteAdIfPresent(driver);
    }

    @Test(priority = 2)
    public void login() {
        homePage.clickSignIn();
        loginPage.login("testuser@example.com", "Test@1234");
    }

    @Test(priority = 3)
    public void addProductToCart() {
        AdHelper.cleanGoogleVignetteFragment(driver);
        AdHelper.closeGoogleVignetteAdIfPresent(driver);
        homePage.searchProduct("Breathe-Easy Tank");
        homePage.selectFirstProduct();
        AdHelper.cleanGoogleVignetteFragment(driver);
        AdHelper.closeGoogleVignetteAdIfPresent(driver);
        homePage.selectFirstProduct();
        productPage.selectSize("M");
        productPage.selectColor("Purple");
        productPage.setQuantity(1);
        productPage.addToCart();
        productPage.waitForAddToCartSuccess();
    }

    @Test(priority = 4)
    public void removeProductFromCart() {
        productPage.openCart();
        Assert.assertTrue(showCartPage.isProductInCartByName("Breathe-Easy Tank"), "Item should be in the cart.");
        showCartPage.removeItemFromCart();
        //    Assert.assertEquals(
        //            showCartPage.getConfirmModalText(),
        //            "Are you sure you would like to remove this item from the shopping cart?"
        //    );

        showCartPage.clickOkOnConfirmationModal();
        Assert.assertTrue(showCartPage.isCartEmpty(), "Cart should be empty after removal.");
    }
}
