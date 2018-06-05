package jaxb;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.XmlElement;

public class Rate {
	private LocalDate date;

	@XmlElement(name = "No")
	private String number;

	private double value;

	public Rate() {
	}

	public LocalDate getDate() {
		return date;
	}

	@XmlElement(name = "EffectiveDate")
	public void setDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.date = LocalDate.parse(date, formatter);
	}

	public double getValue() {
		return value;
	}

	@XmlElement(name = "Mid")
	public void setValue_mid(double mid) {
		value = mid;
	}

	@XmlElement(name = "Bid")
	public void setValue_bid(double bid) {
		value = bid;
	}

	public String getNumber() {
		return number;
	}
}