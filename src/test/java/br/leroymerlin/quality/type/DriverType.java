package br.leroymerlin.quality.type;

public enum DriverType {
	
	/**Chrome*/
	CHROME(true),
	/**Firefox*/
	FIREFOX(false),
	/**Not found*/
	NOT_FOUND(false);
	
	private boolean active;

	DriverType(boolean active) {
		this.active = active;
	}

	/**
	 * Finds active driver set as true
	 * 
	 */
	public static DriverType getActiveDriver() {
		for (DriverType driver : DriverType.values()) {
			if (driver.active){
				return driver;
			}
		}
		return DriverType.NOT_FOUND;
	}
}
