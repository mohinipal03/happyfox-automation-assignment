package Admin_Portal;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webdriverbase.AppPage;

public class Login_Page extends AppPage {
    
    private static final Logger logger = LoggerFactory.getLogger(Login_Page.class);

    public Login_Page(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@type='text' or @type='email']")
    private WebElement usernameField;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[contains(text(),'Login')]")
    private WebElement loginButton;

    @FindBy(xpath = "//span[@class='hf-top-bar_title_text hf-font-light']")
    private WebElement pendingTicketsTitle;

    public void navigateToHappyFoxHomePageURL(String url) {
        logger.info("Navigating to URL: {}", url);
        this.driver.get(url);
    }

    public void enterUsername(String username) {
        logger.info("Entering username: {}", username);
        waitForElementToBeVisible(usernameField);
        clearAndType(usernameField, username);
    }

    public void enterPassword(String password) {
        logger.info("Entering password");
        waitForElementToBeVisible(passwordField);
        clearAndType(passwordField, password);
    }

    public void clickLoginbutton() {
        logger.info("Clicking login button");
        waitForElementToBeClickable(loginButton);
        loginButton.click();
    }

    public AdminPortalTest1stPage validatePendingTicketsTitle() {
        logger.info("Validating pending tickets title");
        try {
            waitForElementToBeVisible(pendingTicketsTitle);
            String titleText = pendingTicketsTitle.getText();
            logger.info("Page title: {}", titleText);
            return new AdminPortalTest1stPage(driver);
        } catch (Exception e) {
            logger.error("Error validating pending tickets title", e);
            throw e;
        }
    }

    public AdminPortalTest1stPage validatePendingTicketsTitle1() {
        logger.info("Validating pending tickets title (alternative method)");
        try {
            waitForElementToBeVisible(pendingTicketsTitle);
            return new AdminPortalTest1stPage(driver);
        } catch (Exception e) {
            logger.error("Error validating pending tickets title", e);
            throw e;
        }
    }
}

class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }
}

class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("loginButton")).click();
    }

    public void forgotPassword() {
        driver.findElement(By.linkText("Forgot password?")).click();
    }
}

class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void verifyHomePage() {
        if (!driver.getCurrentUrl().equals("https://www.happyfox.com/home")) {
            throw new IllegalStateException("Not on the home page");
        }
    }

    public void navigateToProfile() {
        driver.findElement(By.id("profileLink")).click();
    }

    public class TablePage extends BasePage {

        private By rowLocator = By.xpath("//table[@id='dataTable']/tbody/tr");

        public TablePage(WebDriver driver) {
            super(driver);
        }

        public void retrieveRowTexts() {
            List<WebElement> rows = driver.findElements(rowLocator);

            for (int i = 0; i < rows.size(); i++) {
                WebElement row = rows.get(i);
                String rowText = row.getText();
                System.out.println("Row " + i + " Text: " + rowText);
            }
        }
    }
}
