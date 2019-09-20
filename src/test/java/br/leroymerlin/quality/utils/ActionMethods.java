package br.leroymerlin.quality.utils;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ActionMethods.
 */
public class ActionMethods {
	
	/**  The driver. */
	private WebDriver driver;
	
	/**  The wait. */
	private FluentWait<WebDriver> wait;
	
	/**  The log. */
	private static final Logger LOG = LoggerFactory.getLogger(ActionMethods.class);

	/**
	 * Instantiates a new action methods.
	 *
	 * @param driver the driver
	 */
	public ActionMethods(WebDriver driver) {
		this.driver = driver;
		this.wait = new FluentWait<WebDriver>(driver)
				// wait duration 8 seconds
				.withTimeout(Duration.ofSeconds(8))
				// polling every 1 second
				.pollingEvery(Duration.ofSeconds(1))
				// ignore some element reference exceptions
				.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class);
	}
	
	/**
	 * Click on locator with wait time.
	 *
	 * @param locator By
	 */
	public void clickWithReload( By locator) {
		
		try {

			// wait for page load and click
			waitLoadPage();
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			wait.until(ExpectedConditions.elementToBeClickable((locator))).click();
			// wait load page
			waitLoadPage();
		} catch (Exception e) {
			LOG.error("[ActionMethods] Error click with reload: "+locator + " ," + e.getCause());
		}
	}
	
	/**
	 * Click on locator with wait time.
	 *
	 * @param locator By
	 */
	public void click( By locator) {
		
		try {
			
			// click on the element
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
		} catch (Exception e) {
			LOG.error("[ActionMethods] Error click with reload: "+locator + " ," + e.getCause());
		}
	}
	
	/**
	 * Method type the value into field with wait time.
	 *
	 * @param value String
	 * @param locator By
	 */
	public void inputWithReload(String value,By locator) {
		try {
			
			// wait for page load and input value
			waitLoadPage();
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			// clear the field before to type value
			element.sendKeys("\b");
			element.sendKeys(value);
			
			// wait text to be present to continue
			wait.until(ExpectedConditions.textToBePresentInElementValue(locator, value));
		} catch (Exception e) {
			LOG.error("[ActionMethods] Error input with reload: " + value + ", into: " + locator + " ," + e.getCause());
		}
	}
	
	/**
	 * Method type the value into field.
	 *
	 * @param value String
	 * @param locator By
	 */
	public void input(String value, By locator) {

		try {

			// type the value
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(value);

		} catch (Exception e) {
			LOG.error("[ActionMethods] Error input: " + value + ", into: " + locator + " ," + e.getCause());
		}

	}
	
	/**
	 * Finds text of the element.
	 *
	 * @param locator the locator
	 * @return the string
	 */
	public String findTextOfElement(By locator) {

		// the text
		String text = null;

		try {
			// wait for element to be present and sets the text
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			text = Optional.ofNullable(locator)
							// check if element is present before to get text
						   .filter(element -> isElementPresent(element))
						   	// get text
						   .map(element -> {
							   return driver.findElement(element).getText();
						   }).orElseGet(() -> {
							   return new String();
			});

		} catch (Exception e) {
			LOG.error("[ActionMethods] Error to find text of element: " + locator + " ," + e.getCause());
		}

		return text;
	}
	
	/**
	 * Method verify if element is enabled
	 * 
	 */	
	public Boolean isEnabled (By locator) {
		
		boolean isEnabled = false;		
		try {
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			isEnabled = driver.findElement(locator).isEnabled();
			
		}catch(Exception e) {
			LOG.error("[ActionMethods] Erro verificar elemento: "+locator+" habilitado: "+ e.getCause());
		}
		
		return isEnabled;		
	}
	
	/**
	 * Method verify if element is enabled
	 * 
	 */	
	public Boolean isDisplayed(By locator) {
		
		boolean isEnabled = false;		
		try {
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			isEnabled = driver.findElement(locator).isDisplayed();
			
		}catch(Exception e) {
			LOG.error("[ActionMethods] Erro verificar elemento: "+locator+" habilitado: "+ e.getCause());
		}
		
		return isEnabled;		
	}
	
	/**
	 * Wait load page.
	 */
	private void waitLoadPage() {
		try {
        	
        	driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    		Thread.sleep(500);
            ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver driver) {
                            return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                        }
                    };
        	
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(expectation);
        } catch (Throwable e) {
        	LOG.error("ActionMethods] error wait for page load: "+ e.getCause());
        }	
	}
	
	/**
	 * Method verify if element is present .
	 *
	 * @param locator the locator
	 * @return true, if is element present
	 */
	public boolean isElementPresent(By locator) {
		
		boolean present = false;
		try {
			
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			present = driver.findElements(locator).size() > 0;
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} catch (Exception e) {
			LOG.error("[ActionMethods] Error waiting for element: "+ locator +", to be present: "+ e.getCause());
		}
		
		return present;
	}
	
	/**
	 * Method verify if text is present.
	 *
	 * @param text the text
	 * @return Boolean isPresent
	 */
	public Boolean findText(String text) {

		Boolean isPresent = false;

		try {
			waitLoadPage();
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			isPresent = driver.getPageSource().contains(text);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			waitLoadPage();
		} catch (Exception e) {
			LOG.error("[ActionMethods] error to find text: " + text + " ," + e.getCause());
		}

		return isPresent;
	}
	
	
	
	
	/**
	 * Method scroll down the page.
	 */
	public void scrollDown() {
		
		try {
			
			// Scroll
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,250)", "");
		} catch (Exception e) {
			LOG.error("[ActionMethods] Error to scroll down: " + e.getCause());
		}
	}
	
	/**
	 * Method scroll up the page.
	 */
	public void scrollUp() {
		
		try {
			
			// Scroll
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-250)", "");
		} catch (Exception e) {
			LOG.error("[ActionMethods] Error to scroll up: " + e.getCause());
		}
		
		
	}
	
	/**
	 * Method to scroll top page.
	 */
	public void scrollTop(){
		
		try {
			
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-750)", "");
		} catch (Exception e) {
			LOG.error("[ActionMethods] Error to scroll top: " + e.getCause());
		}
	}
	
	/**
	 * Method to scroll down page.
	 */
	public void scrollBottom() {

		try {
			
			// Scroll
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,750)", "");
		} catch (Exception e) {
			LOG.error("[ActionMethods] Error to scroll bottom: " + e.getCause());
		}
	}
}
