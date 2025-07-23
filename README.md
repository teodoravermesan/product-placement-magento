# Magento Automation Testing with Selenium (Java)

## Overview
This repository contains automated test scripts for the Magento demo site [https://magento.softwaretestingboard.com](https://magento.softwaretestingboard.com).  
The tests cover essential user flows including login, logout, cart management, and order placement using the Page Object Model (POM) design pattern.

---

## Technologies Used

- Java 18
- Selenium WebDriver 4.21.0
- TestNG 7.8.0
- Maven
- WebDriverManager
- Allure Reporting
- Surefire Plugin

---

## Features

- **Browser Setup**  
  Supports Chrome, Firefox, and Edge browsers using WebDriverManager for driver management.  
  Tests use TestNG with proper lifecycle management (`@BeforeMethod`, `@AfterMethod`) for clean driver initialization and teardown.

- **Page Object Model (POM)**  
  The test framework follows POM to separate page-specific locators and actions from test logic.  
  This improves readability, maintainability, and reusability of test code.

- **Test Scenarios**  
  - User login (valid and invalid)  
  - Sign out functionality  
  - Cart operations: add, remove, verify item counts  
  - Order placement workflows with modal handling and validation  

- **Reporting**  
  Uses **Allure Reports** for detailed, user-friendly test reports including screenshots, steps, and logs.

---

## How to Run the Tests

1. **Prerequisites:**  
   - Java JDK 11 or higher  
   - Maven 3.x  
   - Chrome, Firefox, or Edge browser installed

2. **Configure Browser**  
   Edit `config.properties` and set the desired browser, for example:  

3. **Run Tests via Maven**  
From the project root directory, run:  mvn clean test

4. **Generate and Open Allure Report**  
After test execution, generate the Allure report:  mvn allure:serve
This command will build and open the Allure report in your default browser.
TestNG Reports are generated automatically under: target/surefire-reports/

---

## Project Structure

src/
└─ main/
└─ java/
└─ base/ # BaseTest class with WebDriver setup/teardown
└─ pages/ # Page classes for different Magento pages
└─ utils/ # Utility classes (ConfigReader, helper methods)
└─ test/
└─ java/
└─ tests/ # Test classes implementing test scenarios

- **BaseTest.java:** Handles WebDriver lifecycle and browser configuration.  
- **Page Classes:** Encapsulate web element locators and page actions.  
- **Test Classes:** Contain TestNG test methods using page objects.

---

## Notes

- **Google Ads Removal:**  
  Ads and Google vignettes are automatically removed during tests using JavaScript injected repeatedly by the utility method `cleanGoogleVignetteFragment()`.  

- **Cookie Consent Handling:**  
  The `acceptCookies()` method detects and clicks the cookie consent popup to prevent test interruptions.

- **Modal Dialogs:**  
  Confirmation popups during cart item removal are handled with appropriate wait and action methods.

---



