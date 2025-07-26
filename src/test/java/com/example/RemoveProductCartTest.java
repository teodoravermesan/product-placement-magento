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
public class RemoveProductCartTest extends BaseTest {
    private HomePage homePage;
    private ProductPage productPage;
    private LoginPage loginPage;
    private ShowCartPage showCartPage;
    private HeaderPage headerPage;
    private RemoveModalPage removeModalPage;

    @BeforeMethod
    public void initPages() {
        logger.info("Initializing page objects for new test.");
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        loginPage = new LoginPage(driver);
        showCartPage = new ShowCartPage(driver);
        headerPage = new HeaderPage(driver);
        removeModalPage = new RemoveModalPage(driver);
    }

    @Test(priority = 1)
    public void openHomePage() {
        logger.info("Opening home page.");
        loadHomePage();
        logger.info("Home page opened and ads handled.");
    }

    @Test(priority = 2)
    public void searchProduct() {
        logger.info("Searching for product: {}", TestData.PRODUCT_NAME);
        homePage.searchProduct(TestData.PRODUCT_NAME);
    }

    @Test(priority = 3)
    public void selectFirstProduct() {
        logger.info("Selecting first product from search results.");
        homePage.selectFirstProduct();
    }

    @Test(priority = 4)
    public void customizeProduct() {
        logger.info("Selecting product size: {}", TestData.SIZE);
        productPage.selectSize(TestData.SIZE);
        logger.info("Selecting product color: {}", TestData.COLOR);
        productPage.selectColor(TestData.COLOR);
        logger.info("Setting product quantity to 1.");
        productPage.setQuantity(1);
    }

    @Test(priority = 5)
    public void addToCart() {
        logger.info("Adding product to cart.");
        productPage.addToCart();
        String successMsg = productPage.getAddToCartSuccessMessage();
        String expectedMsg = TestData.SUCCES_ADD_TO_CART_MESSAGE + TestData.PRODUCT_NAME + TestData.SUCCES_ADD_TO_CART_MESSAGE1;
        logger.info("Add to cart success message: {}", successMsg);
        Assert.assertTrue(successMsg.contains(expectedMsg), "Add to cart message mismatch.");
        productPage.openCart();
        Assert.assertTrue(showCartPage.isItemInCart(TestData.PRODUCT_NAME), "Product is not present in the cart.");
        logger.info("Product is present in the cart.");
    }

    @Test(priority = 6)
    public void removeProductFromCart() {
        logger.info("Removing product from cart.");
        showCartPage.removeItemFromCart();
        logger.info("Confirming removal in modal.");
        Assert.assertTrue(removeModalPage.isRemoveModalDisplayed(), "Remove modal is not displayed.");
        Assert.assertEquals(removeModalPage.getRemoveModalContent(),
                "Are you sure you would like to remove this item from the shopping cart?",
                "Remove modal content mismatch.");
        removeModalPage.clickOkRemoveModal();
        Assert.assertTrue(showCartPage.isCartEmpty(), "Cart is not empty after product removal.");
        logger.info("Cart is empty after product removal.");
    }
}
