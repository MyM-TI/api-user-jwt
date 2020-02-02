package cl.mym.api.user.jwt.model;

import java.io.Serializable;

public class Phone implements Serializable{
	
	private static final long serialVersionUID = 5195923696872134686L;
	
	private int number;
	private int cityCode;
	private int countryCode;
	
	public Phone() {}
	
	public Phone(int number, int cityCode, int countryCode) {
		this.number = number;
		this.cityCode = cityCode;
		this.countryCode = countryCode;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	public int getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(int countryCode) {
		this.countryCode = countryCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cityCode;
		result = prime * result + countryCode;
		result = prime * result + number;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phone other = (Phone) obj;
		if (cityCode != other.cityCode)
			return false;
		if (countryCode != other.countryCode)
			return false;
		if (number != other.number)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Phone [number=" + number + ", cityCode=" + cityCode + ", countryCode=" + countryCode + "]";
	}
	
	

}

