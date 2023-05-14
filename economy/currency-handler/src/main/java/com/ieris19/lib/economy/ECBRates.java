package com.ieris19.lib.economy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;

public class ECBRates {
	private static ECBRates instance;
	private final HashMap<String, BigDecimal> rates;
	private ZonedDateTime lastUpdate;

	public static ECBRates getInstance() {
		if (instance == null)
			instance = new ECBRates();
		if (!instance.isUpToDate())
			instance.update();
		return instance;
	}

	public static Currency convert(Currency from, String codeTo) {
		ECBRates ecb = ECBRates.getInstance();
		if (!from.getCode().equals("EUR")) {
			BigDecimal rate = ecb.getRate(from.getCode());
			if (rate == null)
				throw new IllegalArgumentException("Currency " + from.getCode() + " is not supported by the ECB");
			from = new Currency(from.getValue().divide(rate, 2, RoundingMode.HALF_UP), "EUR");
		}
		BigDecimal transactionRate = ecb.getRate(codeTo);
		if (transactionRate == null)
			throw new IllegalArgumentException("Currency " + codeTo + " is not supported by the ECB");
		return new Currency(from.getValue().multiply(transactionRate), codeTo);
	}

	private ECBRates() {
		rates = new HashMap<String, BigDecimal>();
		//ECB exchange rates are always relative to the Euro, so Euro to Euro rate is always 1
		rates.put("EUR", new BigDecimal(1));
		update();
	}

	public BigDecimal getRate(String code) {
		return rates.get(code);
	}

	boolean isUpToDate() {
		return lastUpdate != null && lastUpdate.isAfter(ZonedDateTime.now().minusDays(1));
	}

	protected void update() {
		URL ecbXMLAddress = null;
		try {
			ecbXMLAddress = new URI("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml").toURL();
		} catch (URISyntaxException | MalformedURLException e) {
			e.printStackTrace();
		}
		if (ecbXMLAddress == null)
			return;
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(ecbXMLAddress.openStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains("Cube currency=")) {
					String[] parts = line.split(" ");
					String code = parts[1].substring(10, 13);
					String rate = parts[2].substring(6, parts[2].length() - 3);
					rates.put(code, new BigDecimal(rate));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		lastUpdate = ZonedDateTime.now(ZoneId.of("UTC"));
	}
}
