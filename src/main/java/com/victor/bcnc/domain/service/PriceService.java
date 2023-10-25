package com.victor.bcnc.domain.service;

import com.victor.bcnc.domain.Price;

public interface PriceService {
    public Price getPrice(String date, Integer productID, Integer brandID);
}
