package com.jpmc.theater;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

public class MovieTests {

	private static final Duration STANDARD_DURATION = Duration.ofMinutes(90);
	private static final double TEN_DOLLAR_MOVIE_PRICE = 10;
	private static final int FIRST_SHOW = 1;
	private static final int SECOND_SHOW = 2;
	private static final double FIRST_SHOW_DISCOUNT_AMT = 3;
	private static final double SECOND_SHOW_DISCOUNT_AMT = 2;
	private static final int SPECIAL_CODE = 1;

	private LocalDateTime testDateTime;
	private Movie testMovie;

	@Before
	public void Setup() {
		// this date is not a Sunday, or the 7th of the month. Therefore, it is a good
		// control.
		testDateTime = LocalDateTime.of(LocalDate.of(2022, 11, 14), LocalTime.of(1, 0, 0, 0));
		testMovie = new Movie("Test-Movie", "Just Testing Movies Here", STANDARD_DURATION, TEN_DOLLAR_MOVIE_PRICE, 19);
	}

	@Test
	// making sure constructor and getters work
	public void createMovie() {
		Movie testMovie = new Movie("Test-Movie", "Just Testing Movies Here", STANDARD_DURATION, TEN_DOLLAR_MOVIE_PRICE,
				32);
		assertEquals(testMovie.getRunningTime(), STANDARD_DURATION);
		assertEquals(testMovie.getTitle(), "Test-Movie");
		assertEquals(testMovie.getTicketPrice(), TEN_DOLLAR_MOVIE_PRICE, 0.001);
	}

	/*
	 * The following tests are ensuring that a movie cannot be created without
	 * proper parameters.
	 */
	@Test(expected = InvalidMovieException.class)
	public void createMovieWithNegitiveDurationThrowsException() {
		new Movie("Test-Movie", "Just Testing Movies Here", Duration.ofMinutes(-90), TEN_DOLLAR_MOVIE_PRICE, 19);
		fail();
	}

	@Test(expected = InvalidMovieException.class)
	public void createMovieWithDurationLongerThan480MinutesThrowsException() {
		new Movie("Test-Movie", "Just Testing Movies Here", Duration.ofMinutes(481), TEN_DOLLAR_MOVIE_PRICE, 19);
		fail();
	}

	@Test(expected = InvalidMovieException.class)
	public void createMovieWithNegitiveTicketPriceThrowsException() {
		new Movie("Test-Movie", "Just Testing Movies Here", Duration.ofMinutes(481), -1, 19);
		fail();
	}

	@Test(expected = InvalidMovieException.class)
	public void emptyTitleThrowsException() {
		new Movie("", "Just Testing Movies Here", STANDARD_DURATION, TEN_DOLLAR_MOVIE_PRICE, SPECIAL_CODE);
		fail();
	}

	@Test(expected = InvalidMovieException.class)
	public void nullTitleThrowsException() {
		new Movie(null, "Just Testing Movies Here", STANDARD_DURATION, TEN_DOLLAR_MOVIE_PRICE, SPECIAL_CODE);
		fail();
	}

	@Test(expected = InvalidMovieException.class)
	public void nullDurationThrowsException() {
		new Movie("Test-Movie", "Just Testing Movies Here", null, TEN_DOLLAR_MOVIE_PRICE, SPECIAL_CODE);
		fail();
	}

	/*
	 * The following tests are testing the discount rules. Each test name is
	 * descriptive of the test nature.
	 */
	@Test
	public void getDiscountOfThreeDollarsForFirstShow() {
		Showing testShowing = new Showing(testMovie, FIRST_SHOW, testDateTime);
		assertEquals(testMovie.getDiscount(testShowing), FIRST_SHOW_DISCOUNT_AMT, 0.00001);
	}

	@Test
	public void noThreeDollarDiscountForNotFirstShow() {
		Showing testShowing = new Showing(testMovie, 5, testDateTime);
		assertNotEquals(testMovie.getDiscount(testShowing), FIRST_SHOW_DISCOUNT_AMT);
	}

	@Test
	public void getTwoDollarDiscountForSecondShow() {
		Showing testShowing = new Showing(testMovie, SECOND_SHOW, testDateTime);
		assertEquals(testMovie.getDiscount(testShowing), SECOND_SHOW_DISCOUNT_AMT, 0.00001);

	}

	@Test
	public void getTwentyPercentDiscountForSpecialCodeShow() {
		Movie specialCodeMovie = new Movie("Test-Movie", "Just Testing Movies Here", STANDARD_DURATION,
				TEN_DOLLAR_MOVIE_PRICE, SPECIAL_CODE);
		Showing testShowing = new Showing(specialCodeMovie, 5, testDateTime);
		assertEquals(specialCodeMovie.getDiscount(testShowing), 2, 0.0001);
	}

	@Test
	public void noTwentyPercentDiscountForNonSpecialCodeShow() {
		Showing testShowing = new Showing(testMovie, 5, testDateTime);
		assertNotEquals(testMovie.getDiscount(testShowing), 2);
	}

	@Test
	public void fiftyPercentDiscountIfMovieIs4thAndLessThan90Min() {
		Movie shortMovie = new Movie("Test-Movie", "Just Testing Movies Here", Duration.ofMinutes(89),
				TEN_DOLLAR_MOVIE_PRICE, 19);
		Showing testShowing = new Showing(shortMovie, 4, testDateTime);
		assertEquals(shortMovie.getDiscount(testShowing), 5, 0.0001);
	}

	@Test
	public void twentyFivePercentDiscountBetween11and4() {
		Showing testShowing = new Showing(testMovie, 6,
				LocalDateTime.of(LocalDate.of(2022, 11, 14), LocalTime.of(12, 0, 0, 0)));
		assertEquals(testMovie.getDiscount(testShowing), 2.5, 0.0001);
	}

	@Test
	public void noTwentyFivePercentDiscountIfBefore11() {
		Showing testShowing = new Showing(testMovie, 6,
				LocalDateTime.of(LocalDate.of(2022, 11, 14), LocalTime.of(10, 59, 0, 0)));
		assertNotEquals(testMovie.getDiscount(testShowing), 2.5);
	}

	@Test
	public void noTwentyFivePercentDiscountIfAfter4() {
		Showing testShowing = new Showing(testMovie, 6,
				LocalDateTime.of(LocalDate.of(2022, 11, 14), LocalTime.of(16, 1, 0, 0)));
		assertNotEquals(testMovie.getDiscount(testShowing), 2.5);
	}

	@Test
	public void oneDollarDiscountForSeventhOfMonth() {
		Showing testShowing = new Showing(testMovie, 6,
				LocalDateTime.of(LocalDate.of(2022, 11, 7), LocalTime.of(16, 1, 0, 0)));
		assertEquals(testMovie.getDiscount(testShowing), 1, 0.0001);
	}

	@Test
	public void noOneDollarDiscountIfNotSeventhOfMonth() {
		Showing testShowing = new Showing(testMovie, 6,
				LocalDateTime.of(LocalDate.of(2022, 11, 8), LocalTime.of(16, 1, 0, 0)));
		assertNotEquals(testMovie.getDiscount(testShowing), 1);
	}

	@Test
	public void fivePercentDiscountForSunday() {
		Showing testShowing = new Showing(testMovie, 6,
				LocalDateTime.of(LocalDate.of(2022, 11, 13), LocalTime.of(16, 1, 0, 0)));
		assertEquals(testMovie.getDiscount(testShowing), 0.50, 0.0001);
	}

	@Test
	public void noFivePercentDiscountForOtherThanSunday() {
		Showing testShowing = new Showing(testMovie, 6,
				LocalDateTime.of(LocalDate.of(2022, 12, 13), LocalTime.of(16, 1, 0, 0)));
		assertNotEquals(testMovie.getDiscount(testShowing), 0.5);
	}

	@Test
	public void biggestDiscountWins() {
		Movie testMovieWithSpecialCode = new Movie("Test-Movie", "Just Testing Movies Here", STANDARD_DURATION,
				TEN_DOLLAR_MOVIE_PRICE, SPECIAL_CODE);
		Showing testShowing = new Showing(testMovieWithSpecialCode, FIRST_SHOW, testDateTime);
		assertEquals(testMovieWithSpecialCode.getDiscount(testShowing), 3, 0.0001); // discount of $3 for first show is
																					// more than %20
		// for special movie
	}

	/*
	 * the following tests are testing the equal method
	 */
	@Test
	public void twoSameMoviesAreEqual() {
		Movie movie1, movie2;
		movie1 = movie2 = new Movie("Test-Movie", "Just Testing Movies Here", STANDARD_DURATION, TEN_DOLLAR_MOVIE_PRICE,
				SPECIAL_CODE);
		assertTrue(movie1.equals(movie2));
	}

	@Test
	public void twoMoviesWithDifferentTitlesAreNotEqual() {
		Movie movie1 = new Movie("Test-Movie", "Just Testing Movies Here", STANDARD_DURATION, TEN_DOLLAR_MOVIE_PRICE,
				SPECIAL_CODE);
		Movie movie2 = new Movie("Different Title", "Just Testing Movies Here", STANDARD_DURATION,
				TEN_DOLLAR_MOVIE_PRICE, SPECIAL_CODE);
		assertFalse(movie1.equals(movie2));
	}

	@Test
	public void twoMoviesWithDifferentDecriptionsAreNotEqual() {
		Movie movie1 = new Movie("Test-Movie", "Just Testing Movies Here", STANDARD_DURATION, TEN_DOLLAR_MOVIE_PRICE,
				SPECIAL_CODE);
		Movie movie2 = new Movie("Test-Movie", "This description should render these movies not equal",
				STANDARD_DURATION, TEN_DOLLAR_MOVIE_PRICE, SPECIAL_CODE);
		assertFalse(movie1.equals(movie2));
	}

	@Test
	public void twoMoviesWithDifferentDurationsAreNotEqual() {
		Movie movie1 = new Movie("Test-Movie", "Just Testing Movies Here", STANDARD_DURATION, TEN_DOLLAR_MOVIE_PRICE,
				SPECIAL_CODE);
		Movie movie2 = new Movie("Test-Movie", "Just Testing Movies Here", Duration.ofMinutes(91),
				TEN_DOLLAR_MOVIE_PRICE, SPECIAL_CODE);
		assertFalse(movie1.equals(movie2));
	}

	/*
	 * Testing the hashCode method
	 */
	@Test
	public void twoEqualMoviesShouldHaveSameHashCode() {
		Movie movie1, movie2;
		movie1 = movie2 = new Movie("Test-Movie", "Just Testing Movies Here", STANDARD_DURATION, TEN_DOLLAR_MOVIE_PRICE,
				SPECIAL_CODE);
		assertEquals(movie1.hashCode(), movie2.hashCode());
	}

	@Test
	public void twoDifferentMoviesShouldHaveDifferentHashCode() {
		Movie movie1 = new Movie("Test-Movie", "Just Testing Movies Here", STANDARD_DURATION, TEN_DOLLAR_MOVIE_PRICE,
				SPECIAL_CODE);
		Movie movie2 = new Movie("Test-Movie", "This description should render these movies not equal",
				STANDARD_DURATION, TEN_DOLLAR_MOVIE_PRICE, SPECIAL_CODE);
		assertTrue(movie1.hashCode() != movie2.hashCode());
	}

}
