package com.jpmc.theater;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Movie {
	// variables we will use for discounts, set as final - they should not change.
	private static final int MOVIE_CODE_SPECIAL = 1;
	private static final double SPECIAL_MOVIE_DISCOUNT_PCT = .2;
	private static final int FIRST_SEQ_DISCOUNT = 3;
	private static final int SECOND_SEQ_DISCOUNT = 2;
	private static final double SHORT_SHOW_DISCOUNT_PCT = .5;
	private static final double SUNDAY_DISCOUNT_PCT = .05;
	private static final double MID_DAY_DISCOUNT_PCT = .25;
	private static final int DAY_7 = 7;
	private static final double DAY_7_DISCOUNT = 1;
	private static final int FOURTH_SEQ_OF_DAY = 4;
	private static final long NINETY_MINUTES = 90;
	private static final int ELEVEN_AM = 11;
	private static final int FOUR_PM = 16;
	private static final int MOVIE_DURATION_MINUTES_CAP = 480;

	private String title;
	private String description;
	@JsonSerialize(using = DurationSerializer.class)
	private Duration runningTime;
	private double ticketPrice;
	private int specialCode;

	public Movie(String title, String description, Duration runningTime, double ticketPrice, int specialCode)
			throws InvalidMovieException {
		if (runningTime == null || runningTime.compareTo(Duration.ofMinutes(0)) < 0
				|| runningTime.compareTo(Duration.ofMinutes(MOVIE_DURATION_MINUTES_CAP)) > 0) {
			throw new InvalidMovieException("Invalid Movie Duration.");
		} else if (ticketPrice < 0) {
			throw new InvalidMovieException("Ticket Price must be $0 or greater.");
		} else if (title == null || title.equals("")) {
			throw new InvalidMovieException("Invalid Title.");
		} else {
			this.title = title;
			this.runningTime = runningTime;
			this.ticketPrice = ticketPrice;
			this.specialCode = specialCode;
			this.description = description;
		}
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Duration getRunningTime() {
		return runningTime;
	}

	public double getTicketPrice() {
		return ticketPrice;
	}

	public double calculateTicketPrice(Showing showing) {
		return ticketPrice - getDiscount(showing);
	}

	// making this method protected so we can test it
	double getDiscount(Showing showing) {
		double discount = 0;
		// %50 discount if the show is less then 90 minutes and 4th sequence
		if (runningTime.compareTo(Duration.ofMinutes(NINETY_MINUTES)) < 0
				&& showing.getSequenceOfTheDay() == FOURTH_SEQ_OF_DAY) {
			discount = ticketPrice * SHORT_SHOW_DISCOUNT_PCT;
		}
		// %25 discount if show is between 11 and 4
		else if (showing.getShowStartTime().getHour() >= ELEVEN_AM && showing.getShowStartTime().getHour() < FOUR_PM) {
			discount = ticketPrice * MID_DAY_DISCOUNT_PCT;
		}
		// 20% discount for special movie
		else if (MOVIE_CODE_SPECIAL == specialCode) {
			discount = ticketPrice * SPECIAL_MOVIE_DISCOUNT_PCT;
		}
		// %5 discount if show is on a Sunday
		else if (showing.getShowStartTime().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
			discount = ticketPrice * SUNDAY_DISCOUNT_PCT;
		}
		if (discount < FIRST_SEQ_DISCOUNT) {
			// $3 discount for 1st show
			if (showing.isFirst()) {
				discount = FIRST_SEQ_DISCOUNT;
			}
			// $2 discount for 2nd show
			else if (showing.isSecond()) {
				discount = discount > SECOND_SEQ_DISCOUNT ? discount : SECOND_SEQ_DISCOUNT;
			}
			// $1 discount if show is on the 7th of the month
			else if (showing.getShowStartTime().getDayOfMonth() == DAY_7) {
				discount = discount > DAY_7_DISCOUNT ? discount : DAY_7_DISCOUNT;
			}
		}
		return discount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Movie movie = (Movie) o;
		return Double.compare(movie.ticketPrice, ticketPrice) == 0 && Objects.equals(title, movie.title)
				&& Objects.equals(description, movie.description) && Objects.equals(runningTime, movie.runningTime)
				&& Objects.equals(specialCode, movie.specialCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, description, runningTime, ticketPrice, specialCode);
	}

	public String toString() {
		return "Title: " + title + "; Description: " + description;
	}
}