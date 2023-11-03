package com.victor.bcnc.domain.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.victor.bcnc.application.error.exceptions.NotFoundException;
import com.victor.bcnc.domain.Price;
import com.victor.bcnc.domain.repository.PriceRepository;

@Service
public class PriceServiceImpl implements PriceService {

	private PriceRepository priceRepository;

	public PriceServiceImpl(PriceRepository priceRepository) {
		this.priceRepository = priceRepository;
	}

	@Override
	public Price getPrice(String date, Integer productID, Integer brandID)
			throws NotFoundException, IllegalArgumentException {

		var localDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		validateInput(localDate, productID, brandID);

		return priceRepository.findByDateAndBrandIDAndProductID(Timestamp.valueOf(localDate), brandID, productID)
				.orElseThrow(NotFoundException::new);
	}

	private void validateInput(LocalDateTime date, Integer productID, Integer brandID) throws IllegalArgumentException {

		if (productID < 1) {
			throw new IllegalArgumentException("product ID must be positive");
		}

		if (brandID < 1) {
			throw new IllegalArgumentException("brand ID must be positive");
		}
	}

}
