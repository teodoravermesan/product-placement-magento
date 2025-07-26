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

- **TestNG Screenshot on Failure Utility**
  - Takes screenshots when a test method fails
  - Saves screenshots to the `screenshots` folder in the project root.
  - Screenshots are named after the failed test method.
  - Automatically creates the `screenshots` folder if it doesn’t exist.
    
- **Logging** 

This project uses SLF4J with Logback for logging test execution details.

  - Logger is initialized in `BaseTest` and inherited by all test classes.
  - Logs capture key test steps like setup, actions, and validations.

---

## Setup Instructions

### Clone the Repository

To get started, clone this repository to your local machine:

```bash
git clone https://github.com/your-username/your-repo-name.git
cd your-repo-name
```

### How to Run the Tests

1. **Prerequisites:**  
   - Java JDK 11 or higher  
   - Maven 3.x  
   - Chrome, Firefox, or Edge browser installed

2. **Configure Browser**  
   Edit `config.properties` and set the desired browser

3. **Build the Project and Download Dependencies**  
If you use Maven, run:  mvn clean install

4. **Run Tests via Maven**  
From the project root directory, run:  mvn clean test

5. **Generate and Open Allure Report**  
After test execution, generate the Allure report:  mvn allure:serve
This command will build and open the Allure report in your default browser.
6. **Generate and Open TestNG Reports**  
TestNG Reports are generated automatically under: target/surefire-reports/

---

## Project Structure

```
src/
 ├── screenshots/    <-- screenshots saved here
 ├── logs/    <-- logs saved here
 └─ main/
     └─ java/
         ├─ base/      # BaseTest class with WebDriver setup/teardown
         ├─ data/      # TestData class with Strings
         ├─ pages/     # Page classes for different Magento pages
         ├─ reports/   # Contains classes for managing test reporting
         └─ utils/     # Utility classes (ConfigReader, AdHelper, CookieConsentHandler etc)
         └─ resources/ # Controls log levels, formats, and output destinations. and environment settings

test/
 └─ java/
     └─ tests/    # Test classes implementing test scenarios
```

This structure follows the **Page Object Model (POM)**, improving code reuse, readability, and maintainability. Each page class encapsulates the page-specific elements and actions, while test classes in `tests/` orchestrate these actions to implement real-world test scenarios.


- **BaseTest.java:** Handles WebDriver lifecycle and browser configuration.  
- **Page Classes:** Encapsulate web element locators and page actions.  
- **Test Classes:** Contain TestNG test methods using page objects.

---

## Notes

- **Google Ads Removal:**  
  Ads and Google vignettes are automatically removed during tests using JavaScript injected repeatedly by the utility method `cleanGoogleVignetteFragment()`.  

- **Cookie Consent Handling:**  
  The `acceptCookies()` method detects and clicks the cookie consent popup to prevent test interruptions.

---
