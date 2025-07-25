package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigReader;
import utils.CookieConsentHandler;
import utils.ScreenshotUtil;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected WebDriver driver;
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        if (driver == null) {
            logger.info("Initializing WebDriver and ExtentReports...");
            String browser = ConfigReader.get("browser");

            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--headless");
                    options.addArguments("--disable-gpu");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-blink-features=AutomationControlled");
                    options.addArguments("--disable-features=BlockThirdPartyCookies,IsolateOrigins,site-per-process");
                    options.addArguments("--disable-background-networking");
                    options.addArguments("--disable-client-side-phishing-detection");
                    options.addArguments("start-maximized");
                    options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                    options.setExperimentalOption("useAutomationExtension", false);
                    options.addArguments("disable-infobars");
                    Map<String, Object> prefs = new HashMap<>();
                    prefs.put("profile.default_content_setting_values.cookies", 1); // Allow all cookies
                    prefs.put("profile.block_third_party_cookies", false); // Allow 3rd-party cookies
                    prefs.put("network.cookie.cookieBehavior", 0); // 0 = Allow all cookies (if supported)
                    options.setExperimentalOption("prefs", prefs);
                    driver = new ChromeDriver(options);
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;
                default:
                    throw new RuntimeException("Unsupported browser: " + browser);
            }

            driver.manage().window().maximize();
        }
    }

    protected void loadHomePage() {
        String url = ConfigReader.get("base.url");
        driver.get(url);
        CookieConsentHandler.acceptConsent(driver);
    }


    @AfterMethod
    public void captureScreenshotOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            ScreenshotUtil.takeScreenshot(driver, result.getName());
        }
    }


    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }

    }
}
