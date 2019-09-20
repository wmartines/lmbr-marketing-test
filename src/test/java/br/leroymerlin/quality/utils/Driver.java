package br.leroymerlin.quality.utils;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.leroymerlin.quality.type.DriverType;

/**
 * The Class Driver.
 */
public class Driver {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(Driver.class);

	/** The Constant time. */
	private final static int time = 30;

	/**
	 * Get active driver.
	 *
	 * @param url String
	 * @return WebDriver
	 */
	public static WebDriver getDriver(String url) {

		// Finds active driver
		DriverType activeDriver = DriverType.getActiveDriver();

		// Check if it's firefox
		if (activeDriver.equals(DriverType.FIREFOX)) {
			return firefoxDriver(url);
		}

		// Check uf it's chrome
		else if (activeDriver.equals(DriverType.CHROME)) {
			return chromeDriver(url);
		}

		// starts firefox by default
		return firefoxDriver(url);
	}

	/**
	 * Starts firefox driver.
	 *
	 * @param url String
	 * @return WebDriver
	 */
	private static WebDriver firefoxDriver(String url) {

		WebDriver driver = null;
		try {

			System.setProperty("webdriver.gecko.driver", new File("").getAbsolutePath() + "/driver/geckodriver");
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
			driver.get(url);

		} catch (Exception e) {
			LOG.error("[Driver] Erro ao iniciar driver firefox: " + e.getCause());
		}
		return driver;
	}

	/**
	 * Starts chrome driver.
	 *
	 * @param url String
	 * @return WebDriver
	 */
	private static WebDriver chromeDriver(String url) {
		WebDriver driver = null;
		try {
			System.setProperty("webdriver.chrome.driver", new File("").getAbsolutePath() + "/driver/chromedriver");
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
			driver.get(url);
			driver.manage().window().setSize(new Dimension(1366, 768));

		} catch (Exception e) {

			LOG.error("[Driver] Erro ao iniciar driver chrome: " + e.getCause());
		}
		return driver;
	}
}
