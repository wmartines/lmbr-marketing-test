package br.leroymerlin.quality.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.leroymerlin.quality.pojo.ProductPojo;
import br.leroymerlin.quality.type.ItemFilterType;
import br.leroymerlin.quality.utils.ActionMethods;

/**
 * The Class ProductPage.
 */
public class ProductPage {
	
	/** The action. */
	private ActionMethods action;
	
	/**
	 * Instantiates a new product page.
	 *
	 * @param driver the driver
	 */
	public ProductPage(WebDriver driver) {
		action = new ActionMethods(driver);
	}
	
	/**
	 * Filter item.
	 *
	 * @param filterType the filter type
	 */
	public void filterItem(ItemFilterType filterType) {
		
		Optional.ofNullable(filterType)
				.ifPresent(filter->{
					action.clickWithReload(By.xpath("(//a[@data-facet='"+filterType.getLocator()+"'])[2]"));
				});
	}
	
	/**
	 * Find product.
	 *
	 * @param productIndex the product index
	 */
	public ProductPojo findProduct(Integer productIndex) {
		
		ProductPojo product = new ProductPojo();
		
		Optional.ofNullable(productIndex)
				.ifPresent(index ->{
					product.setPrice(action.findTextOfElement(By.xpath("(.//span[@class='price-integer'])["+index+"]")));
					product.setName(action.findTextOfElement(By.xpath("(.//div[@class='name hidden-mobile'])["+index+"]")));
					
				});
		
		return product;
	}
	
	/**
	 * Prints the products.
	 *
	 * @param productQty the product qty
	 */
	public void printProducts(Integer productQty) {
		
		List<ProductPojo> products = new ArrayList<>();
		
		// find products
		action.scrollDown();
		for(int i = 1;  i <= productQty; i++) {
			products.add(findProduct(i));
		}
		
		// print products
		for (ProductPojo productPojo : products) {
			System.out.println("Product: "+productPojo.getName());
			System.out.print("Price: "+productPojo.getPrice()+"\n");
		}
		System.out.println(">>>>>>>>");
	}
}
