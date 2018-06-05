package jaxb;

import java.io.StringReader;
import java.util.Arrays;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class CurrenciesGetter {
	private final String currencyTable;
	private final String currencyCode;
	private final Integer topCount;

	private final String url;

	CurrenciesGetter(String table, String code, Integer count) throws NBPException {

		if (table == null || table.isEmpty() || !Arrays.asList("A", "B", "C", "a", "b", "c").contains(table)) {
			throw new NBPException("Table must be either A, B or C.");
		}

		if (code == null || code.length() != 3) {
			throw new NBPException("Currency must contain 3 letters.");
		}

		if (count == null || count <= 0) {
			throw new NBPException("Count must be greater than 0.");
		}

		currencyTable = table;
		currencyCode = code;
		topCount = count;

		String urlTemplate = "http://api.nbp.pl/api/exchangerates/rates/_TABLE_/_CURRENCY_/last/_COUNT_/";

		url = urlTemplate.replaceAll("_TABLE_", currencyTable).replaceAll("_CURRENCY_", currencyCode)
				.replaceAll("_COUNT_", topCount.toString());
	}

	public RatesSeries getRatesSeries() throws NBPException {

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(UriBuilder.fromUri(url).build());
		String data = target.request(MediaType.TEXT_XML).get(String.class);

		System.out.println(data);

		RatesSeries ratesSeries = null;

		try {
			JAXBContext jaxb = JAXBContext.newInstance(RatesSeries.class);
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();
			StringReader reader = new StringReader(data);
			ratesSeries = (RatesSeries) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new NBPException("Found an error while getting data: " + e.getMessage());
		}

		return ratesSeries;

	}
}
