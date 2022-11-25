package com.jpmc.theater;

import java.time.LocalDate;

public class MockLocalDateProvider implements LocalDateProviderInterface {
	private static MockLocalDateProvider instance = null;
	private LocalDate testDate;

	/**
	 * private constructor. cannot be called from outside this class.
	 */
	private MockLocalDateProvider() {
	}

	/**
	 * @return make sure to return singleton instance
	 */
	public static MockLocalDateProvider getSingletonInstance() {
		synchronized (LocalDateProvider.class) {
			if (instance == null) {
				instance = new MockLocalDateProvider();
			}
		}
		return instance;
	}

	/**
	 * 
	 * @param date date to set for testing
	 */
	public void setDate(LocalDate date) {
		testDate = date;
	}

	/**
	 * @return LocalDate set for testing
	 */
	public LocalDate currentDate() {
		return testDate;
	}
}
