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
        logger.info("Initializing page objects for a new test.");
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        loginPage = new LoginPage(driver);
        showCartPage = new ShowCartPage(driver);
    }

    @Test(priority = 1)
    public void openHomePage() {
        logger.info("Initializing browser state for new test");
        loadHomePage();
        logger.info("Home page loaded successfully");
        AdHelper.cleanGoogleVignetteFragment(driver);
        AdHelper.closeGoogleVignetteAdIfPresent(driver);
    }

    @Test(priority = 2)
    public void login() {
        logger.info("Starting login with user: {}", TestData.VALID_USERNAME);
        homePage.clickSignIn();
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);
        Assert.assertTrue(loginPage.getWelcomeMessage().contains(TestData.WELCOME_MESSAGE));
        logger.info("Login submitted.");
    }

    @Test(priority = 3)
    public void searchProduct() {
        logger.info("Preparing to search for product: {}", TestData.PRODUCT_NAME);
        AdHelper.cleanGoogleVignetteFragment(driver);
        AdHelper.closeGoogleVignetteAdIfPresent(driver);
        logger.info("Search for product");
        homePage.searchProduct(TestData.PRODUCT_NAME);
        logger.info("Search executed for product: {}", TestData.PRODUCT_NAME);
    }

    @Test(priority = 4)
    public void selectFirstProduct() {
        logger.info("Selecting first product from the search results.");
        homePage.selectFirstProduct();
        AdHelper.cleanGoogleVignetteFragment(driver);
        AdHelper.closeGoogleVignetteAdIfPresent(driver);
        homePage.selectFirstProduct();
        logger.info("First product selected.");
    }

    @Test(priority = 5)
    public void customizeProduct() {
        logger.info("Customizing product with size: {} and color: {}", TestData.SIZE, TestData.COLOR);
        productPage.selectSize(TestData.SIZE);
        productPage.selectColor(TestData.COLOR);
        productPage.setQuantity(1);
        logger.info("Product customization done.");
    }

    @Test(priority = 6)
    public void addToCart() {
        logger.info("Adding product to cart.");
        productPage.addToCart();
        String successMsg = productPage.getAddToCartSuccess();
        logger.info("Add to cart success message: {}", successMsg);
        Assert.assertTrue(productPage.getAddToCartSuccess().contains(TestData.SUCCES_ADD_TO_CART_MESSAGE + TestData.PRODUCT_NAME + TestData.SUCCES_ADD_TO_CART_MESSAGE1));
        productPage.openCart();
        boolean productInCart = showCartPage.isProductInCartByName(TestData.PRODUCT_NAME);
        logger.info("Is product '{}' present in cart? {}", TestData.PRODUCT_NAME, productInCart);
        Assert.assertTrue(showCartPage.isProductInCartByName(TestData.PRODUCT_NAME));
    }
}
