package app_example.car_example;

import doodle.D;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Car car = new Car();
		car.driver = new Person("Michael Schumacher");
		car.addPassenger(new Person("Carl Johnson"));
		
		D.raw(car);
	}

}
