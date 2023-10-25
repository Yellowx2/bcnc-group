package com.victor.bcnc.application.error;

import java.time.LocalDateTime;

public record ErrorMessage(
		int statusCode,
		LocalDateTime timestamp,
		String message) {
}
