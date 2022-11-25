package com.jpmc.theater;

import java.util.Objects;

public class Customer {

	private String name;
	private int id;
	private static int idGenerator = 100;

	/**
	 * @param name customer name
	 */
	public Customer(String name) {
		this.id = getAvailableId();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	private static int getAvailableId() {
		idGenerator++;
		return idGenerator;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Customer))
			return false;
		Customer customer = (Customer) o;
		return Objects.equals(name, customer.name) && Objects.equals(id, customer.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, id);
	}

	@Override
	public String toString() {
		return "Name: " + name;
	}
}