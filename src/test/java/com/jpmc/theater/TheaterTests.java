package com.jpmc.theater;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.time.Duration;
import java.time.LocalDate;
import org.junit.*;

public class TheaterTests {

	// a mock provider must be used, otherwise the tests are dependent on the day of
	// the month and the day of the week.
	private MockLocalDateProvider mockProvider;
	private Theater theater;

	@Before
	public void setUp() {
		mockProvider = MockLocalDateProvider.getSingletonInstance();
		this.mockProvider.setDate(LocalDate.of(2022, 11, 23));
		theater = new Theater(mockProvider);
	}

	/*
	 * testing .reserve() method, making sure it calculates ticket price correctly. 
	 */
	@Test
	public void testReserveMethodCalculatesTotalFeeWith20PercentDiscount() {
		Customer john = new Customer("John Doe");
		Reservation reservation = theater.reserve(john, 8, 4);
		System.out.println("You have to pay " + reservation.totalFee());
		assertEquals(reservation.totalFee(), 40, 0.0001);
	}

	@Test
	public void testReserveMethodCalculatesTotalFeeWithFirstOfDay() {
		Customer john = new Customer("John Doe");
		Reservation reservation = theater.reserve(john, 1, 4);
		assertEquals(reservation.totalFee(), 32, 0.0001);
	}

	/*
	 * test print methods - only way to fail is if it crashes. This will not test WHAT is printed. 
	 */
	@Test
	public void printMovieSchedule() {
		theater.printSchedule();
	}

	@Test
	public void printScheduleInJson() {
		theater.printScheduleInJson();
	}
	
	
}
