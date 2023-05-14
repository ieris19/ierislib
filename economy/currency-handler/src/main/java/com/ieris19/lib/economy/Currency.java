package com.ieris19.lib.economy;

import java.math.BigDecimal;

public class Currency {
	private BigDecimal value;
	private final String code;

	public Currency(BigDecimal value, String code) {
		this.value = value;
		this.code = code;
	}

	public Currency(double value, String code) {
		this.value = new BigDecimal(value);
		this.code = code;
	}

	public Currency(String code) {
		this.value = new BigDecimal(0);
		this.code = code;
	}

	public Currency(BigDecimal value) {
		this.value = value;
		this.code = "EUR";
	}

	public Currency(double value) {
		this.value = new BigDecimal(value);
		this.code = "EUR";
	}

	public BigDecimal getValue() {
		return value;
	}

	public String getCode() {
		return code;
	}
}
