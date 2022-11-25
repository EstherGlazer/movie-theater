package com.jpmc.theater;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class CustomerTests {

	@Test
	public void createCustomerTest() {
		Customer testCust = new Customer("Esther Glazer");
		assertTrue(testCust.getName().equals("Esther Glazer"));
	}

	@Test
	// the difference in ID of the two consecutive customers should be 1.
	public void custIdGeneratorTest() {
		Customer testCust1 = new Customer("John Doe");
		Customer testCust2 = new Customer("Esther Glazer");
		assertTrue(testCust2.getId() - testCust1.getId() == 1);
	}
	
	@Test
	// equals method checks the name and the ID
	// Note: this also proves that the Id's are unique. 
	public void customerEqualsMethodTest() {
		Customer testCust1 = new Customer("Esther Glazer");
		Customer testCust2 = new Customer("Esther Glazer");
		assertFalse(testCust1.equals(testCust2));
	}

}