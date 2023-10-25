package com.victor.bcnc.application.rest;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.victor.bcnc.application.error.exceptions.NotFoundException;
import com.victor.bcnc.application.response.PriceResponse;
import com.victor.bcnc.domain.service.PriceService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/prices")
@Log4j2
public class PriceController {

	private final PriceService priceService;

	public PriceController(PriceService priceService) {
		this.priceService = priceService;
	}

	/**
	 * Retrieves the price of a product in a specific date
	 * 
	 * @param date      Date and time of the product price using
	 *                  'yyyy-MM-dd HH:mm:ss' format
	 * @param productID Numeric ID of a product
	 * @param brandID   Numeric ID of a brand
	 * @return PriceResponse Product price information
	 */
	@GetMapping
	public PriceResponse getPrice(@RequestParam String date, @RequestParam Integer productID,
			@RequestParam Integer brandID) throws NotFoundException {

		log.info(String.format("obtaining price with parameters:\n%s", Map.of(
				"date", date,
				"productID", productID,
				"brandID", brandID).toString()));

		var price = this.priceService.getPrice(date, productID, brandID);

		if (price == null) {
			throw new NotFoundException();
		}

		var response = new PriceResponse(price.brandID(), price.startDate(), price.endDate(), price.productID(),
				price.price(),
				price.currency());

		log.info(String.format("price successfully obtained:\n%s", response.toString()));

		return response;
	}

}
