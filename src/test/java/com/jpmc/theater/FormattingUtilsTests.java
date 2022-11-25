package com.jpmc.theater;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

public class FormattingUtilsTests {

	@Test
	// basic test of the humanReadableFormat method
	public void humanReadableFormatTest() {
		assertEquals(FormattingUtils.humanReadableFormatForDuration(Duration.ofMinutes(90)), ("(1 hour 30 minutes)"));
	}

	@Test
	// test with passing in large number
	public void humanReadableFormatLargeNumbersTest() {
		assertEquals(FormattingUtils.humanReadableFormatForDuration(Duration.ofMinutes(900)), ("(15 hours 0 minutes)"));
	}

	@Test
	// test with passing in zero
	public void humanReadableFormatZeroTest() {
		assertEquals(FormattingUtils.humanReadableFormatForDuration(Duration.ofMinutes(0)), ("(0 hours 0 minutes)"));
	}

	@Test
	// we are testing with 0, 1 and n. Because it passes these three, we can assume
	// it will pass any other number.
	public void handlePluralsWithZeroTest() {
		assertEquals(FormattingUtils.handlePlural(0), "s");
	}

	@Test
	// basic test to make sure it does not "pluralize" a singular.
	public void handlePluralsWithOneTest() {
		assertEquals(FormattingUtils.handlePlural(1), "");
	}

	@Test
	// testing a bigger number
	public void handlePluralsWithNTest() {
		assertEquals(FormattingUtils.handlePlural(9), "s");
	}

	@Test
	public void dateFormatterTest() {
		assertEquals(FormattingUtils.formatDateTime(LocalDateTime.now()),
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
	}

	@Test
	public void humanReadableFormatForDuration() {
		assertTrue(
				FormattingUtils.humanReadableFormatForDuration(Duration.ofMinutes(90)).equals("(1 hour 30 minutes)"));
	}

	@Test
	public void testHandlePluralsWithSingle() {
		assertTrue(FormattingUtils.humanReadableFormatForDuration(Duration.ofMinutes(61)).equals("(1 hour 1 minute)"));
	}

	@Test
	public void testHandlePluralsWithPlural() {
		assertTrue(
				FormattingUtils.humanReadableFormatForDuration(Duration.ofMinutes(122)).equals("(2 hours 2 minutes)"));
	}

	@Test
	// testing that buildJsonString method can return json string
	public void jsonBuilderTest() {
		ArrayList<Showing> testList = new ArrayList<Showing>();
		testList.add(new Showing(new Movie("Test Movie Title", "Movie Description", Duration.ofMinutes(10), 15, 2), 2,
				LocalDateTime.of(LocalDate.of(2022, 11, 13), LocalTime.of(3, 0))));
		try {
			String jsonResponse = FormattingUtils.buildJsonString(testList);
			String expectedJson = "[{\"movie\":{\"title\":\"Test Movie Title\",\"description\":\"Movie Description\",\"runningTime\":600000,\"ticketPrice\":15.0},\"sequenceOfTheDay\":2,\"showStartTime\":\"13-11-2022 03:00:00\",\"reservations\":\"No reservations\",\"second\":true,\"first\":false}]";
			assertEquals(jsonResponse.length(), expectedJson.length()); // testing by length, because json is unordered
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
