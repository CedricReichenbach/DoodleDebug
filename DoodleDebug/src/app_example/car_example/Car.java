package app_example.car_example;

import java.util.ArrayList;
import java.util.List;

public class Car {
	 
	public Person driver;
	
	private List<Person> passengers;
	
	public Car() {
		this.passengers = new ArrayList<Person>();
	}
	
	public void addPassenger(Person passenger) {
		this.passengers.add(passenger);
	}

}
