package br.leroymerlin.quality.type;

public enum ItemFilterType {
	
	/** The highest price first*/
	HIGHEST_FIRST("melhor-preco"),
	
	/** The lowest price first*/
	LOWEST_FIRST("menor-preco"),
	
	/** The only offers*/
	ONLY_OFFERS("ofertas"),
	
	/** The buy by the internet*/
	BUY_BY_INTERNET("comprar-on-line"),
	
	/** The not found*/
	NOT_FOUND("Not found");
	
	/** The locator*/
	private String locator ;
	
	ItemFilterType(String locator) {
		this.locator = locator;
	}
	
	/** Gets the locator*/
	public String getLocator() {
		return locator;
	}
}
