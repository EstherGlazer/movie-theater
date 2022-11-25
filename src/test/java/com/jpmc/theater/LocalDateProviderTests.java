package com.jpmc.theater;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import org.junit.Test;

public class LocalDateProviderTests {

	@Test // this test might fail if it is run at 12:00 AM, due to race conditions.
	public void currentSingletonDateIsAccurate() {
		assertEquals(LocalDateProvider.getSingletonInstance().currentDate(), LocalDate.now());
	}

	// make sure there is only one instance of LocalDateTime Object. No need for
	// more than one.
	@Test
	public void onlyOneLocalDateProviderInstance() {
		LocalDateProvider providerOne = LocalDateProvider.getSingletonInstance();
		LocalDateProvider providerTwo = LocalDateProvider.getSingletonInstance();
		assertEquals(providerOne, providerTwo);
	}
}
