package com.ieris19.lib.economy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ECBRatesTest {

	@Test void isUpToDate() {
		ECBRates rates = ECBRates.getInstance();
		assertTrue(rates.isUpToDate());
	}

	@Test void isNotUpToDate() {
		ECBRates rates = ECBRates.getInstance();
		ZonedDateTime oldUpdate = ZonedDateTime.now().minusDays(5);
		try {
			Field update = rates.getClass().getDeclaredField("lastUpdate");
			update.setAccessible(true);
			update.set(rates, oldUpdate);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
			fail();
		}
		assertFalse(rates.isUpToDate());
	}

	@Test void update() {
		ECBRates rates = ECBRates.getInstance();
		HashMap<String, BigDecimal> data = null;
		try {
			Field ecbRate = rates.getClass().getDeclaredField("rates");
			ecbRate.setAccessible(true);
			data = (HashMap<String, BigDecimal>) ecbRate.get(rates);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
			fail();
		}
		String[] currencies = {"USD", "JPY", "BGN", "CZK", "DKK", "GBP", "HUF", "PLN", "RON", "SEK", "CHF", "ISK", "NOK",
													 "TRY", "AUD", "BRL", "CAD", "CNY", "HKD", "IDR", "ILS", "INR", "KRW", "MXN", "MYR", "NZD",
													 "PHP", "SGD", "THB", "ZAR", "EUR"};
		for (String code : currencies) {
			assertTrue(data.containsKey(code));
		}
	}

	@Test void convert() {
		ECBRates rates = ECBRates.getInstance();
		try {
			Field ecbRate = ECBRates.class.getDeclaredField("rates");
			ecbRate.setAccessible(true);
			HashMap<String, BigDecimal> data = (HashMap<String, BigDecimal>) ecbRate.get(rates);
			data.put("USD", new BigDecimal("0.8"));
			ecbRate.set(rates, data);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
			fail();
		}
		BigDecimal expected = new BigDecimal(125).setScale(2, RoundingMode.HALF_UP);
		Currency currency = new Currency(new BigDecimal(100), "USD");
		Currency converted = ECBRates.convert(currency, "EUR");
		assertEquals("EUR", converted.getCode());
		assertEquals(expected, converted.getValue());
	}
}
