package jaxb;

import java.io.StringWriter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Path("exchangerates/rates")
public class ExchangeRates {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{table}/{code}/{topCount}")
	public Response getAverageExchange_Text(@PathParam("table") String table, @PathParam("code") String code,
			@PathParam("topCount") Integer count) {

		double avg = 0;

		try {
			CurrenciesGetter getter = new CurrenciesGetter(table, code, count);
			RatesSeries ratesSeries = getter.getRatesSeries();
			avg = ratesSeries.getAverageExchangeRate();

		} catch (NBPException e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}

		return Response.ok(avg).build();
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("{table}/{code}/{topCount}")
	public Response getAverageExchange_Html(@PathParam("table") String table, @PathParam("code") String code,
			@PathParam("topCount") Integer count) {

		double avg = 0;

		try {
			CurrenciesGetter getter = new CurrenciesGetter(table, code, count);
			RatesSeries ratesSeries = getter.getRatesSeries();
			avg = ratesSeries.getAverageExchangeRate();

		} catch (NBPException e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}

		return Response.ok("<html><body><h1>" + avg + "</h1></body></html>").build();
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("{table}/{code}/{topCount}")
	public Response getAverageExchange_Xml(@PathParam("table") String table, @PathParam("code") String code,
			@PathParam("topCount") Integer count) {

		double avg = 0;

		try {
			CurrenciesGetter getter = new CurrenciesGetter(table, code, count);
			RatesSeries ratesSeries = getter.getRatesSeries();
			avg = ratesSeries.getAverageExchangeRate();

		} catch (NBPException e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}

		StringWriter writer = new StringWriter();

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();

			Element rootElement = doc.createElement("AverageExchangeRate");
			rootElement.setTextContent(String.valueOf(avg));
			doc.appendChild(rootElement);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(writer);
			transformer.transform(source, result);

		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.ok(writer.toString()).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{table}/{code}/{topCount}")
	public Response getAverageExchange_Json(@PathParam("table") String table, @PathParam("code") String code,
			@PathParam("topCount") Integer count) {

		double avg = 0;

		try {
			CurrenciesGetter getter = new CurrenciesGetter(table, code, count);
			RatesSeries ratesSeries = getter.getRatesSeries();
			avg = ratesSeries.getAverageExchangeRate();
		} catch (NBPException e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}

		JsonObject json = Json.createObjectBuilder().add("AverageExchangeRate", avg).build();

		return Response.ok(json.toString()).build();
	}
}