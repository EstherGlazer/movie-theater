package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Theater {

	private LocalDateProviderInterface provider;
	private List<Showing> schedule;

	public Theater(LocalDateProviderInterface provider) {
		if (provider != null) {
			this.provider = provider;
			try {
				Movie spiderMan = new Movie("Spider-Man: No Way Home", "New dramatic Spider-Man Movie",
						Duration.ofMinutes(90), 12.5, 1);
				Movie turningRed = new Movie("Turning Red", "A Story of somthing or someone Turning Red",
						Duration.ofMinutes(85), 11, 0);
				Movie theBatMan = new Movie("The Batman", "An excilarating Batman story", Duration.ofMinutes(95), 9, 0);

				schedule = List.of(
						new Showing(turningRed, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0))),
						new Showing(spiderMan, 2, LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0))),
						new Showing(theBatMan, 3, LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 50))),
						new Showing(turningRed, 4, LocalDateTime.of(provider.currentDate(), LocalTime.of(14, 30))),
						new Showing(spiderMan, 5, LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10))),
						new Showing(theBatMan, 6, LocalDateTime.of(provider.currentDate(), LocalTime.of(17, 50))),
						new Showing(turningRed, 7, LocalDateTime.of(provider.currentDate(), LocalTime.of(19, 30))),
						new Showing(spiderMan, 8, LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10))),
						new Showing(theBatMan, 9, LocalDateTime.of(provider.currentDate(), LocalTime.of(23, 0))));
			} catch (InvalidMovieException e) {
				System.out.println(e.getMessage());
			} catch (InvalidShowingException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public Reservation reserve(Customer customer, int sequence, int howManyTickets) {
		Showing showing;
		try {
			showing = schedule.get(sequence - 1);
		} catch (RuntimeException ex) {
			throw new IllegalStateException("not able to find any showing for given sequence " + sequence);
		}
		return new Reservation(customer, showing, howManyTickets);
	}

	public void printSchedule() {
		System.out.println("Date: " + provider.currentDate());
		System.out.println("Schedule: ");
		System.out.println("===================================================");
		if (schedule != null) {
			schedule.forEach(s -> System.out.println(s.toString()));
			System.out.println("===================================================");
		}
	}

	public void printScheduleInJson() {
		if (schedule != null) {
			String json = null;
			try {
				json = FormattingUtils.buildJsonString(schedule);
				System.out.println("Json Format:\n" + json);
			} catch (JsonProcessingException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public List<Showing> getSchedule() {
		return schedule;
	}
}
