package app_example.ownDrawMethod;

public class PhoneNumber {
	final int countryCode;
	final String number;
	
	PhoneNumber(int countryCode, String number) {
		this.countryCode = countryCode;
		this.number = number;
	}
	
	public String toString() {
		return "+" + String.valueOf(countryCode)+ " " + number;
	}
}

