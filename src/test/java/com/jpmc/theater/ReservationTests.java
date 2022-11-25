package com.jpmc.theater;

import static org.junit.Assert.assertTrue;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.*;

public class ReservationTests {
	private Customer customer;
	private Movie movie;
	private Showing showing;
	private LocalDateTime dateTimeTest;

	@Before
	public void setUp() {
		this.customer = new Customer("John Doe");
		this.movie = new Movie("Spider-Man: No Way Home", "New dramatic Spider-Man Movie", Duration.ofMinutes(90), 12.5,
				5);
		this.dateTimeTest = LocalDateTime.of(LocalDate.of(2022, 11, 14), LocalTime.of(17, 0, 0));
		this.showing = new Showing(movie, 6, dateTimeTest);
	}

	@Test
	public void totalFeeOfReservation() {
		assertTrue(new Reservation(customer, showing, 3).totalFee() == 37.5);
	}

	@Test
	public void totalFeeofReservationWithFirstSequenceDiscount() {
		assertTrue(new Reservation(customer, new Showing(movie, 1, dateTimeTest), 2).totalFee() == 19);
	}
}
