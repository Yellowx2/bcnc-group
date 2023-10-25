package com.victor.bcnc.application.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public record PriceResponse(
		Integer brandID,
		LocalDateTime startDate,
		LocalDateTime endDate,
		Integer productId,
		BigDecimal price,
		Currency curr) {

}
