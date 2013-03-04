package doodle.model;

public class PhoneNumber {
	private String areaCode;
	private String rest;

	public PhoneNumber(String areaCode, String rest) {
		super();
		this.areaCode = areaCode;
		this.rest = rest;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getRest() {
		return rest;
	}

}
