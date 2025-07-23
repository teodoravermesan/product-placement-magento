package com.example;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import pages.LoginPage;
import utils.CookieConsentHandler;

@Epic("Regression Tests")
@Feature("Login")
@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class LoginTest extends BaseTest {
    private HomePage homePage;
    private LoginPage loginPage;

    @BeforeMethod
    public void initPages() {
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        homePage.loadHomePage();
        CookieConsentHandler.acceptConsent(driver);
    }

    @Test(description = "Verify login works with invalid credentials")
    public void invalidLogin() {
        homePage.clickSignIn();
        loginPage.login("testr@example.com", "1234567");
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("The account sign-in was incorrect"),
                "Expected error message not displayed, actual: " + error);
    }

    @Test(description = "Verify login works with valid credentials")
    public void validLogin() {
        homePage.clickSignIn();
        loginPage.login("testuser@example.com", "Test@1234");

        String welcomeText = loginPage.getWelcomeMessage();
        Assert.assertTrue(welcomeText.contains("Welcome"), "Welcome message missing or incorrect: " + welcomeText);
    }

    @Test(description = "Verify sign out")
    public void signOut() {
        homePage.clickSignIn();
        loginPage.login("testuser@example.com", "Test@1234");
        loginPage.signOut();
        Assert.assertTrue(homePage.isSignInVisible(), "Sign out failed or sign-in link not visible");
    }
}
