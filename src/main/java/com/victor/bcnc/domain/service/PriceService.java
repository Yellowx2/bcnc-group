package com.victor.bcnc.domain.service;

import com.victor.bcnc.application.error.exceptions.NotFoundException;
import com.victor.bcnc.domain.Price;

public interface PriceService {

	/**
	 * Validates and calls repository to obtain the price of a product
	 * 
	 * @param date
	 * @param productID
	 * @param brandID
	 * @return Price
	 * 
	 * @throws NotFoundException
	 * @throws IllegalArgumentException
	 */
	public Price getPrice(String date, Integer productID, Integer brandID)
			throws NotFoundException, IllegalArgumentException;
}
