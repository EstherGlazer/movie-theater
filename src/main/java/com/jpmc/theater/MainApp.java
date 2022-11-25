package com.jpmc.theater;

import java.util.Scanner;

public class MainApp {
	private static Scanner keyboard = new Scanner(System.in);

	public static void main(String[] args) {
		Theater theater = new Theater(LocalDateProvider.getSingletonInstance());
		System.out.println("Welcome to the Movie Theater!");
		// this will be terminated when the user chooses the "exit" menu option
		while (true) {
			chooseMenuOption(displyMenu(), theater);
		}
	}

	private static int displyMenu() {
		int menuChoice = 0;
		while (menuChoice < 1 || menuChoice > 5) {
			System.out.println("\nChoose a number option from the menu below:");
			System.out.println(
					"1. See showing schedule \n2. Make a reservation \n3. See Reservations \n4. Print showing schedule in Json \n5. Exit");
			try {
				menuChoice = getInputAndParse();
			} catch (Exception e) {
				System.out.println("Invalid choice.");
			}
		}
		return menuChoice;

	}

	private static void chooseMenuOption(int menuChoice, Theater theater) {
		switch (menuChoice) {
		case 1:
			theater.printSchedule();
			break;
		case 2:
			createReservationMenuOption(theater);
			break;
		case 3:
			seeReservationsMenuOption(theater);
			break;
		case 4:
			theater.printScheduleInJson();
			break;
		case 5:
			System.out.println("Exiting....Goodbye.");
			System.exit(0);
		}
	}

	private static void seeReservationsMenuOption(Theater theater) {
		int sequence = 0;
		while (sequence < 1 || sequence > 9) {
			System.out.println("Sequence of Showing to see: ");
			try {
				sequence = getInputAndParse();
			} catch (Exception e) {
				System.out.println("Invalid choice. Sequence must be 1 through 9.");
			}
		}
		System.out.println(theater.getSchedule().get(sequence - 1).getReservations());
	}

	private static void createReservationMenuOption(Theater theater) {
		System.out.println("Customer Name: ");
		String name = keyboard.nextLine();
		int sequence = 0;
		while (sequence < 1 || sequence > 9) {
			System.out.println("Sequence of Show: ");
			try {
				sequence = getInputAndParse();
			} catch (Exception e) {
				System.out.println("Invalid choice. Sequence must be 1 through 9.");
			}
		}
		int numOfTickets = 0;
		while (numOfTickets < 1) {
			System.out.println("Number of Tickets: ");
			try {
				numOfTickets = getInputAndParse();
			} catch (Exception e) {
				System.out.println("Invalid number of tickets. Must reserve at least 1 ticket.");
			}
		}
		Customer newCustomer = new Customer(name);
		Reservation newReservation = theater.reserve(newCustomer, sequence, numOfTickets);
		// add reservation to the list of reservations for the specific showing
		theater.getSchedule().get(sequence - 1).addReservation(newReservation);
		System.out.println("Reservation made for: \n" + newReservation.toString() + "\nfor movie: \n"
				+ newReservation.getShowing().getMovie().toString());
	}

	private static int getInputAndParse() {
		String input = keyboard.nextLine();
		return Integer.parseInt(input);
	}
}
