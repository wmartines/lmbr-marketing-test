package br.leroymerlin.quality.page;

import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.leroymerlin.quality.type.ItemFilterType;
import br.leroymerlin.quality.utils.ActionMethods;

/**
 * The Class LeroyMerlinHomePage.
 */
public class LeroyMerlinHomePage {
	
	/** The action. */
	private ActionMethods action;
	
	/** The location modal identification text. */
	private static final String LOCATION_MODAL_TEXT = "Qual cidade você está?";
	
	/**
	 * The constructor.
	 *
	 * @param driver the driver
	 */
	public LeroyMerlinHomePage(WebDriver driver) {
		this.action = new ActionMethods(driver);
	}
	
	/**
	 * Search item.
	 *
	 * @param cep the cep
	 * @param item the item
	 */
	public void searchItem(String item) {
		
		// search item and scroll page to the top
		Optional.ofNullable(item)
				.ifPresent(product->{
					action.scrollTop();
					action.inputWithReload(item, By.xpath(".//input[@class='search-input']"));
					action.clickWithReload(By.xpath("//button[@class='search-button color-neutral']"));
					action.scrollTop();
				});
	}
	
	
	/**
	 * Populate location modal.
	 *
	 * @param cep the cep
	 */
	public void populateLocationModal(String cep) {
		
		Optional.ofNullable(cep)
				.filter(zipCode -> isLocationModalPresent().equals(true))
				.map(zipCode -> {
					action.input(zipCode, By.xpath(".//input[@name='zipcode']"));
					action.clickWithReload(By.xpath(".//button[contains(text() , 'Confirmar')]"));
					return true;})
				.orElse(false);
	}
	
	/**
	 * Check if location modal is present on home screen.
	 *
	 * @return the boolean
	 */
	private Boolean isLocationModalPresent() {
		return action.isElementPresent(By.xpath(".//input[@name='zipcode']"));
	}
}
