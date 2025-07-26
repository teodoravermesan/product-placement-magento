package com.example;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;
import data.TestData;

@Epic("Regression Tests")
@Feature("Order Placement")
@Listeners({utils.ExtentTestListener.class, io.qameta.allure.testng.AllureTestNg.class})
public class AddProductCartTest extends BaseTest {
    private HomePage homePage;
    private ShowCartPage showCartPage;
    private ProductPage productPage;
    private LoginPage loginPage;
    private HeaderPage headerPage;

    @BeforeMethod
    public void initPages() {
        logger.info("Initializing page objects for a new test.");
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        loginPage = new LoginPage(driver);
        showCartPage = new ShowCartPage(driver);
        headerPage = new HeaderPage(driver);
    }

    @Test(priority = 1)
    public void openHomePage() {
        logger.info("Opening home page.");
        loadHomePage();
        logger.info("Home page opened and ads handled.");
    }

    @Test(priority = 2, dependsOnMethods = {"openHomePage"})
    public void login() {
        logger.info("Starting login with user: {}", TestData.VALID_USERNAME);
        headerPage.clickSignIn();
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);
        Assert.assertTrue(loginPage.getWelcomeMessage().contains(TestData.WELCOME_MESSAGE));
        logger.info("Login submitted.");
    }

    @Test(priority = 3, dependsOnMethods = {"login"})
    public void searchProduct() {
        logger.info("Searching for product: {}", TestData.PRODUCT_NAME);
        homePage.searchProduct(TestData.PRODUCT_NAME);
        logger.info("Search completed.");
    }

    @Test(priority = 4, dependsOnMethods = {"searchProduct"})
    public void selectFirstProduct() {
        logger.info("Selecting first product from the search results.");
        homePage.selectFirstProduct();
        logger.info("First product selected.");
    }

    @Test(priority = 5, dependsOnMethods = {"selectFirstProduct"})
    public void customizeProduct() {
        logger.info("Customizing product with size: {} and color: {}", TestData.SIZE, TestData.COLOR);
        productPage.selectSize(TestData.SIZE);
        productPage.selectColor(TestData.COLOR);
        productPage.setQuantity(1);
        logger.info("Product customization done.");
    }

    @Test(priority = 6, dependsOnMethods = {"customizeProduct"})
    public void addToCart() {
        logger.info("Adding product to cart.");
        productPage.addToCart();
        productPage.waitForAddToCartSuccessMessage();
        String successMsg = productPage.getAddToCartSuccessMessage();
        logger.info("Add to cart success message: {}", successMsg);
        String expectedMsg = TestData.SUCCES_ADD_TO_CART_MESSAGE + TestData.PRODUCT_NAME + TestData.SUCCES_ADD_TO_CART_MESSAGE1;
        Assert.assertTrue(successMsg.contains(expectedMsg), "Add to cart message mismatch.");
        productPage.openCart();
        boolean productInCart = showCartPage.isItemInCart(TestData.PRODUCT_NAME);
        logger.info("Is product '{}' present in cart? {}", TestData.PRODUCT_NAME, productInCart);
        Assert.assertTrue(showCartPage.isItemInCart(TestData.PRODUCT_NAME));
    }
}
