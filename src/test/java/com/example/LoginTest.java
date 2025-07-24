package com.example;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import pages.LoginPage;
import utils.TestData;

@Epic("Regression Tests")
@Feature("Login")
@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class LoginTest extends BaseTest {
    private HomePage homePage;
    private LoginPage loginPage;

    @BeforeMethod
    public void initPages() {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        homePage.loadHomePage();
    }

    @Test(description = "Verify login works with invalid credentials")
    public void invalidLogin() {
        homePage.clickSignIn();
        loginPage.login(TestData.INVALID_USERNAME, TestData.INVALID_PASSWORD);
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains(TestData.ERROR_MESSAGE_LOGIN));
    }

    @Test(description = "Verify login works with valid credentials")
    public void validLogin() {
        homePage.clickSignIn();
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);
        String welcomeText = loginPage.getWelcomeMessage();
        Assert.assertTrue(welcomeText.contains(TestData.WELCOME_MESSAGE));
    }

    @Test(description = "Verify sign out")
    public void signOut() {
        homePage.clickSignIn();
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);
        loginPage.signOut();
        Assert.assertTrue(homePage.isSignInVisible());
    }
}
