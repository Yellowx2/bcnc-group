package com.victor.bcnc.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

/**
 * Price record
 */
public record Price(
		Integer brandID,
		LocalDateTime startDate,
		LocalDateTime endDate,
		Integer priceList,
		Integer productID,
		Integer priority,
		BigDecimal price,
		Currency currency) {
}
