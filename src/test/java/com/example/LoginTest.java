package com.example;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HeaderPage;
import pages.HomePage;
import pages.LoginPage;
import utils.TestData;

@Epic("Regression Tests")
@Feature("Login")
@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class LoginTest extends BaseTest {
    private LoginPage loginPage;
    private HeaderPage headerPage;

    @BeforeMethod
    public void initPages() {
        logger.info("Initializing test: resetting browser state");
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        loginPage = new LoginPage(driver);
        headerPage = new HeaderPage(driver);
        loadHomePage();
        logger.info("Home page loaded successfully");
    }

    @Test(description = "Verify login works with invalid credentials")
    public void invalidLogin() {
        logger.info("Starting test: invalidLogin");
        headerPage.clickSignIn();
        logger.info("Clicked on Sign In");
        loginPage.login(TestData.INVALID_USERNAME, TestData.INVALID_PASSWORD);
        logger.info("Attempted login with invalid credentials");
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains(TestData.ERROR_MESSAGE_LOGIN));
        logger.info("Invalid login assertion passed");
    }

    @Test(description = "Verify login works with valid credentials")
    public void validLogin() {
        logger.info("Starting test: validLogin");
        headerPage.clickSignIn();
        logger.info("Clicked on Sign In");
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);
        logger.info("Logged in with valid credentials");
        String welcomeText = loginPage.getWelcomeMessage();
        Assert.assertTrue(welcomeText.contains(TestData.WELCOME_MESSAGE));
        logger.info("Valid login assertion passed");
    }

    @Test(description = "Verify sign out")
    public void signOut() {
        logger.info("Starting test: signOut");
        headerPage.clickSignIn();
        logger.info("Clicked on Sign In");
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);
        logger.info("Logged in successfully");
        headerPage.signOut();
        logger.info("Clicked on Sign Out");
        Assert.assertTrue(headerPage.isSignInVisible());
        logger.info("Sign out test passed");
    }
}
