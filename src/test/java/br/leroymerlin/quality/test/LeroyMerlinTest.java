package br.leroymerlin.quality.test;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;

import br.leroymerlin.quality.page.LeroyMerlinHomePage;
import br.leroymerlin.quality.page.ProductPage;
import br.leroymerlin.quality.type.ItemFilterType;
import br.leroymerlin.quality.utils.Driver;

@FixMethodOrder(MethodSorters.DEFAULT)
public class LeroyMerlinTest {
	
private static WebDriver driver;
	
	/** The Constant URL. */
	private static final String URL = "https://www.leroymerlin.com.br/";
	
	/** The zip code*/
	private static final String ZIP_CODE = "03294100";
	
	/** The home page*/
	private static LeroyMerlinHomePage homePage;
	
	/** The product page*/
	private static ProductPage productPage;
	
	/** The Constant PRODUCT_QTY. */
	private static final Integer PRODUCT_QTY = 5;
	
	
	@BeforeClass
	public static void setUp() {
		
		driver = Driver.getDriver(URL);
		homePage = new LeroyMerlinHomePage(driver);
		productPage = new ProductPage(driver);
	}
	
	@Test
	public void filterItemAByHighestPrice() {
		
		// populate location modal
		homePage.populateLocationModal(ZIP_CODE);
		
		// search item
		homePage.searchItem("Furadeira");
		
		// filter item by highest price
		productPage.filterItem(ItemFilterType.HIGHEST_FIRST);
		
		// print products
		productPage.printProducts(PRODUCT_QTY);
	}
	
	@Test
	public void filterItemAByLowestPrice() {
		
		// search item 
		homePage.searchItem("Furadeira");
		
		// filter item by highest price
		productPage.filterItem(ItemFilterType.LOWEST_FIRST);
		
		// print products
		productPage.printProducts(PRODUCT_QTY);
	}
	
	@Test
	public void filterItemBByHighestPrice() {
		
		// search item 
		homePage.searchItem("Piso");
		
		// filter item by highest price
		productPage.filterItem(ItemFilterType.HIGHEST_FIRST);
		
		// print products
		productPage.printProducts(PRODUCT_QTY);
	}
	
	@Test
	public void filterItemBByLowestPrice() {
		
		// search item with zip code and item
		homePage.searchItem("Piso");
		
		// filter item by highest price
		productPage.filterItem(ItemFilterType.LOWEST_FIRST);
		
		// print products
		productPage.printProducts(PRODUCT_QTY);
	}

}
