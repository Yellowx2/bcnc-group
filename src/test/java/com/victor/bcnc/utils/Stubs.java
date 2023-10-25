package com.victor.bcnc.utils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Currency;

import com.victor.bcnc.domain.Price;

public class Stubs {

	public static Price createPrice() {
		return new Price(1, LocalDateTime.now(), LocalDateTime.now().plus(Period.ofDays(1)), 1, 1, 0,
				new BigDecimal(10.0), Currency.getInstance("EUR"));
	}
}
