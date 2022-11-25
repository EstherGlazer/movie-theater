package com.jpmc.theater;

import static org.junit.Assert.*;
import java.time.*;
import org.junit.*;

public class ShowingTests {

	private MockLocalDateProvider mockProvider;
	private LocalDateTime dateOfTestShowing;
	private Showing testShowing;
	private Movie movie;

	@Before
	public void setUp() {
		this.movie = new Movie("Test-Movie", "Just Testing Movies Here", Duration.ofMinutes(90), 10, 32);
		this.mockProvider = MockLocalDateProvider.getSingletonInstance();
		this.mockProvider.setDate(LocalDate.of(2022, 11, 23));
		this.dateOfTestShowing = LocalDateTime.of(mockProvider.currentDate(), LocalTime.of(18, 30));
		this.testShowing = new Showing(this.movie, 1, dateOfTestShowing);
	}

	@Test
	public void createShowing() {
		// check the correct showing is created
		assertEquals(testShowing.getMovie(), this.movie);
		assertEquals(testShowing.getSequenceOfTheDay(), 1);
		assertEquals(testShowing.getShowStartTime().getDayOfMonth(), dateOfTestShowing.getDayOfMonth());
		assertEquals(testShowing.getShowStartTime().getYear(), dateOfTestShowing.getYear());
	}

	/*
	 * testing bad parameters
	 */
	@Test(expected = InvalidShowingException.class)
	public void showWithNullMovieThrowsException() {
		new Showing(null, 2, dateOfTestShowing);
		fail();
	}

	@Test(expected = InvalidShowingException.class)
	public void showWithNegitiveSequenceThrowsException() {
		new Showing(movie, -2, dateOfTestShowing);
		fail();
	}

	@Test
	public void sequenceOfTheDayTest() {
		assertTrue(testShowing.isSequence(1));
	}

	/*
	 * testing getTicketPrice method
	 */
	@Test
	public void fullMoviePriceTest() {
		assertEquals(testShowing.getMovie().getTicketPrice(), 10, 0.001);
	}

	@Test
	public void calculateFeeWithFirstMovieOfTheDay() {
		assertEquals(testShowing.getMovie().calculateTicketPrice(testShowing), 7, 0.001); // first movie of the day
	}

	/*
	 * testing calculateTicketPrice with second movie of the day
	 */
	@Test
	public void calculateFeeWithSecondMovieOfTheDay() {
		Showing testShowing = new Showing(movie, 2, dateOfTestShowing);
		assertEquals(testShowing.getMovie().calculateTicketPrice(testShowing), 8, 0.001);
	}

	/*
	 * testing Reservations List
	 */
	@Test
	public void reservationsListShouldStartEmpty() {
		Showing testShowing = new Showing(movie, 2, dateOfTestShowing);
		assertEquals(testShowing.getReservations(), "No reservations");
	}

	@Test
	public void reservationsListShouldHaveReservation() {
		Showing testShowing = new Showing(movie, 2, dateOfTestShowing);
		testShowing.addReservation(new Reservation(new Customer("Esther"), testShowing, 2));
		assertNotEquals(testShowing.getReservations(), "No reservations");
	}

	/*
	 * input validation for addReservation
	 */
	@Test
	public void cantAddNullReservation() {
		testShowing.addReservation(null);
		assertEquals(testShowing.getReservations(), "No reservations");
	}
}
