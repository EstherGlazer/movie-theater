package com.jpmc.theater;

public class Reservation {
	private Customer customer;
	private Showing showing;
	private int audienceCount;

	public Reservation(Customer customer, Showing showing, int audienceCount) {
		this.customer = customer;
		this.showing = showing;
		this.audienceCount = audienceCount;
	}

	public int getAudienceCount() {
		return audienceCount;
	}

	public Showing getShowing() {
		return showing;
	}
	public double totalFee() {
		return showing.getMovie().calculateTicketPrice(showing) * audienceCount;
	}

	public String toString() {
		return "Customer: " + customer + "\nShowing: " + showing + "\nAudience Count: " + audienceCount;
	}
}