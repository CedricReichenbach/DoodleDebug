package app_example.car;

import ch.unibe.scg.doodle.D;

public class Main {

	public static void main(String[] args) {
		Car car = new Car(new Person("Enzo Ferrari"));
		car.driver = new Person("Michael Schumacher");
		car.addPassenger(new Person("Carl Johnson"));
		
		D.raw(car);
	}

}
