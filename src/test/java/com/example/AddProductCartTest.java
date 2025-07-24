package com.example;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;
import pages.ShowCartPage;
import utils.AdHelper;
import utils.TestData;

@Epic("Regression Tests")
@Feature("Order Placement")
@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class AddProductCartTest extends BaseTest {
    private HomePage homePage;
    private ShowCartPage showCartPage;
    private ProductPage productPage;
    private LoginPage loginPage;

    @BeforeMethod
    public void initPages() {
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        loginPage = new LoginPage(driver);
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
    public void searchProduct() {
        AdHelper.cleanGoogleVignetteFragment(driver);
        AdHelper.closeGoogleVignetteAdIfPresent(driver);
        homePage.searchProduct(TestData.PRODUCT_NAME);
    }

    @Test(priority = 4)
    public void selectFirstProduct() {
        homePage.selectFirstProduct();
        AdHelper.cleanGoogleVignetteFragment(driver);
        AdHelper.closeGoogleVignetteAdIfPresent(driver);
        homePage.selectFirstProduct();
    }

    @Test(priority = 5)
    public void customizeProduct() {
        productPage.selectSize(TestData.SIZE);
        productPage.selectColor(TestData.COLOR);
        productPage.setQuantity(1);
    }

    @Test(priority = 6)
    public void addToCart() {
        productPage.addToCart();
        Assert.assertTrue(productPage.getAddToCartSuccess().contains(TestData.SUCCES_ADD_TO_CART_MESSAGE + TestData.PRODUCT_NAME + TestData.SUCCES_ADD_TO_CART_MESSAGE1));
        productPage.openCart();
        Assert.assertTrue(showCartPage.isProductInCartByName(TestData.PRODUCT_NAME));
    }
}
