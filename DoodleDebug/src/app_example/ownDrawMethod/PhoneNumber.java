package app_example.ownDrawMethod;

import java.io.Serializable;

public class PhoneNumber implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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

