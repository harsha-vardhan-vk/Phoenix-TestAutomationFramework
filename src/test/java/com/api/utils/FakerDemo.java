package com.api.utils;

import java.util.Locale;

import com.github.javafaker.Faker;

public class FakerDemo {

	public static void main(String[] args) {
		// We will be using the faker library for our fake test data creation

		// We will be creating a fakeUtil that uses this faker library!

		Faker faker = new Faker(new Locale("en-IND"));
		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		String buildingNumber = faker.address().buildingNumber();
		String mobileNumber = faker.number().digits(10);
		String altMobileNumber = faker.numerify("704#######");
		String emailAddress = faker.internet().emailAddress();
	}
}
