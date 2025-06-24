package webdriverbase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
    
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    private WebDriver driver;
    private String browserName = "chrome"; // default browser

    @BeforeSuite
    @Parameters({"browser"})
    public void beforeSuite(String browser) {
        if (browser != null && !browser.isEmpty()) {
            this.browserName = browser.toLowerCase();
        }
        
        logger.info("Starting test suite with browser: {}", browserName);
        driver = initializeDriver(browserName);
        
        if (driver != null) {
            driver.manage().window().maximize();
            logger.info("Browser initialized successfully");
        } else {
            logger.error("Failed to initialize browser");
            throw new RuntimeException("Failed to initialize WebDriver");
        }
    }
    
    private WebDriver initializeDriver(String browser) {
        WebDriver driver = null;
        
        try {
            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-extensions");
                    driver = new ChromeDriver(chromeOptions);
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
                    logger.warn("Browser '{}' not supported. Using Chrome as default.", browser);
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
            }
        } catch (Exception e) {
            logger.error("Error initializing WebDriver for browser: {}", browser, e);
            throw new RuntimeException("Failed to initialize WebDriver", e);
        }
        
        return driver;
    }
    
    @AfterSuite
    public void afterSuite() {
        if (driver != null) {
            try {
                logger.info("Closing browser");
                driver.quit();
                logger.info("Browser closed successfully");
            } catch (Exception e) {
                logger.error("Error closing browser", e);
            }
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
    
    public String getBrowserName() {
        return browserName;
    }
}


