package app_example.car;

import java.util.ArrayList;
import java.util.List;

public class Car {

	public Person driver;
	public Person builder;

	private List<Person> passengers;

	public Car(Person builder) {
		this.passengers = new ArrayList<Person>();
		this.builder = builder;
	}

	public void addPassenger(Person passenger) {
		this.passengers.add(passenger);
	}

}
