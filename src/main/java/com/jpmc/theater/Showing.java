package com.jpmc.theater;

import java.time.LocalDateTime;
import java.util.LinkedList;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


public class Showing {
	private Movie movie;
	private int sequenceOfTheDay;
	 @JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime showStartTime;
	private LinkedList<Reservation> reservations;

	public Showing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) throws InvalidShowingException {
		if (movie == null) {
			throw new InvalidShowingException("Invalid Movie.");
		} else if (sequenceOfTheDay < 1) {
			throw new InvalidShowingException("Invalid sequence.");
		} else {
			this.movie = movie;
			this.sequenceOfTheDay = sequenceOfTheDay;
			this.showStartTime = showStartTime;
		}
	}

	public Movie getMovie() {
		return movie;
	}

	public LocalDateTime getShowStartTime() {
		return showStartTime;
	}

	public boolean isSequence(int sequence) {
		return this.sequenceOfTheDay == sequence;
	}

	public int getSequenceOfTheDay() {
		return sequenceOfTheDay;
	}

	public boolean isFirst() {
		return sequenceOfTheDay == 1;
	}

	public boolean isSecond() {
		return sequenceOfTheDay == 2;
	}

	public void addReservation(Reservation newReservation) {
		if (newReservation != null) {
			if (reservations == null) {
				reservations = new LinkedList<Reservation>();
			}
			reservations.add(newReservation);
		}
	}

	public String getReservations() {
		if (reservations != null) {
			return reservations.toString();
		} else {
			return "No reservations";
		}
	}

	public String toString() {
		return this.getSequenceOfTheDay() + ": " + FormattingUtils.formatDateTime(this.getShowStartTime()) + " "
				+ this.getMovie().getTitle() + " "
				+ FormattingUtils.humanReadableFormatForDuration(this.getMovie().getRunningTime()) + " $"
				+ String.format("%.2f", this.getMovie().getTicketPrice());
	}
}
