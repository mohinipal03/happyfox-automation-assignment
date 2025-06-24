package webdriverbase;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppPage {
	
	private static final Logger logger = LoggerFactory.getLogger(AppPage.class);
	
	// FIX NEEDED: Move these to configuration file (config.properties)
	// Hard-coded paths should be externalized
	public static String PATH_TO_TEST_DATA_FILE = "src/main/resources/";
	public static String WINDOWS_PATH_TO_TEST_DATA_DIR = "src/main/resources/";
	
	// FIX NEEDED: 60 seconds wait time is too high, should be 10-15 seconds max
	// Also should be moved to configuration file
	public static int WAIT_TIME_SEC = 60; // TODO: Reduce to 15 seconds and move to config
	protected WebDriver driver;
	
	JavascriptExecutor javaScriptExecutor;
	
	public AppPage(WebDriver driver) {
		this.driver = driver;
		waitImplicitly();
		PageFactory.initElements(driver, this);
		maximizeWindow();
		logger.debug("AppPage initialized for: {}", this.getClass().getSimpleName());
	}
	
	public WebDriver getDriver() {
		return this.driver;
	}

	public void get(String url) {
		this.driver.get(url);
	}

	public String getCurrentUrl() {
		return this.driver.getCurrentUrl();
	}
	
	public void maximizeWindow() {
			driver.manage().window().maximize();		
	}
	
	// FIX NEEDED: Remove implicit waits completely - they interfere with explicit waits
	// Use only explicit waits for better control and reliability
	// SHOULD BE REMOVED: Implicit waits cause unpredictable behavior
	public void waitImplicitly() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(WAIT_TIME_SEC));
	}

	// FIX NEEDED: Remove this method - implicit waits should not be used
	public void waitImplicitly(int timeOutInSeconds) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeOutInSeconds));
	}
	
	public void clearAndType(WebElement element, String text) {
		element.clear();
		element.sendKeys(text);
	}
	
	public void switchToDefaultContent() {
		this.driver.switchTo().defaultContent();
	}
	
	public void switchToFrame(WebElement frame) {
		this.driver.switchTo().frame(frame);
	}
	
	public void hoverOverElementUsingJS(WebElement element) {
		String js = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
		getJavaScriptExecutor().executeScript(js, element);
	}
	
	public JavascriptExecutor getJavaScriptExecutor() {
		if (javaScriptExecutor == null)
			javaScriptExecutor = (JavascriptExecutor) driver;
		return javaScriptExecutor;
	}
	
	public void scrolltoElement(String locator) {
		try {
			WebElement element = this.driver.findElement(By.xpath(locator));

			scrolltoElement(element);
		} catch (Exception ex) {
			// FIX NEEDED: Empty catch block - should log the exception
			// SHOULD BE: logger.error("Failed to scroll to element with locator: " + locator, ex);
			// throw new RuntimeException("Element not found: " + locator, ex);
		}
	}
	
	// FIX NEEDED: Remove Thread.sleep() and use explicit wait instead
	// Thread.sleep() makes tests unreliable and slow
	public void scrolltoElement(WebElement element) throws InterruptedException {
		getJavaScriptExecutor().executeScript("arguments[0].scrollIntoView(false)", element);
		Thread.sleep(1000); // TODO: Replace with WebDriverWait for element to be stable
	}
	
	public void waitForVisible(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME_SEC));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void waitForElementToBeVisible(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME_SEC));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForElementToBeClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIME_SEC));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	
	public String getCurrentWorkingDirectory()
	{
		String workingDir = null;
		try{
			workingDir = System.getProperty("user.dir");
		}catch(Exception e){
			e.printStackTrace();
		}
		return workingDir;
	}
	
	public String getTestDataFullDirPath(String fileName)
	{
		String path = PATH_TO_TEST_DATA_FILE;
		if(getOperatingSystemType() == OSType.Windows)
			path = WINDOWS_PATH_TO_TEST_DATA_DIR;
		return (getCurrentWorkingDirectory()+ path+ fileName);
	}
	
	public enum OSType {
	    Windows, MacOS, Linux, Other
	  };
	  
	 protected static OSType detectedOS;
	 
	 // FIX NEEDED: This method always returns Windows - should detect actual OS
	 // SHOULD BE: Proper OS detection using System.getProperty("os.name")
	 public static OSType getOperatingSystemType()
	 {
	  // TODO: Implement proper OS detection instead of hard-coding Windows
	  // String osName = System.getProperty("os.name").toLowerCase();
	  // if (osName.contains("win")) return OSType.Windows;
	  // else if (osName.contains("mac")) return OSType.MacOS;
	  // else if (osName.contains("nix") || osName.contains("nux")) return OSType.Linux;
	  // else return OSType.Other;
	  detectedOS = OSType.Windows;
	  return detectedOS;
	 }
	

}
