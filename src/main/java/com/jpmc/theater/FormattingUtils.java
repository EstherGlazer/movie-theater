package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class FormattingUtils {
	private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

	private FormattingUtils() {
		//final class - no public constructor
	}

	public static String humanReadableFormatForDuration(Duration duration) {
		long hour = duration.toHours();
		long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());
		return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin,
				handlePlural(remainingMin));
	}

	// (s) post-fix should be added to handle plural correctly
	static String handlePlural(long value) {
		if (value == 1) {
			return "";
		} else {
			return "s";
		}
	}

	public static String formatDateTime(LocalDateTime date) {
		return date.format(DATE_TIME_FORMATTER);
	}

	public static String buildJsonString(List<Showing> schedule) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(schedule);
	}
}
