package jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ExchangeRatesSeries")
public class RatesSeries {
	@XmlElement(name = "Table")
	private String currencyTable;

	@XmlElement(name = "Code")
	private String currencyCode;

	@XmlElement(name = "Currency")
	private String currencyName;

	private List<Rate> rates = new ArrayList<>();

	@XmlElementWrapper(name = "Rates")
	@XmlElements(@XmlElement(name = "Rate"))
	public List<Rate> getRates() {
		return rates;
	}

	public void setRates(List<Rate> rates) {
		this.rates = rates;
	}

	public String getTable() {
		return currencyTable;
	}

	public String getCode() {
		return currencyCode;
	}

	public String getName() {
		return currencyName;
	}

	public double getAverageExchangeRate() {
		double sum = 0;

		for (Rate rate : rates) {
			sum += rate.getValue();
		}

		return sum / rates.size();
	}
}
