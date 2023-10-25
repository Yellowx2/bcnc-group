package com.victor.bcnc.domain.service;

import com.victor.bcnc.domain.Price;

public interface PriceService {

	/**
	 * Validates and calls repository to obtain the price of a product
	 * 
	 * @param date
	 * @param productID
	 * @param brandID
	 * @return Price
	 */
	public Price getPrice(String date, Integer productID, Integer brandID);
}
