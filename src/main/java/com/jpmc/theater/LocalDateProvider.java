package com.jpmc.theater;

import java.time.LocalDate;

public class LocalDateProvider implements LocalDateProviderInterface {
	
	private static LocalDateProvider instance = null;

	private LocalDateProvider() { //not accessible
	}

	/**
	 * @return make sure to return singleton instance
	 */
	public static LocalDateProvider getSingletonInstance() {
		synchronized (LocalDateProvider.class) {
			if (instance == null) {
				instance = new LocalDateProvider();
			}
		}
		return instance;
	}

	/**
	 * 
	 * @return LocalDate.now()
	 */
	public LocalDate currentDate() {
		return LocalDate.now();
	}
}
