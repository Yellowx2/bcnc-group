package com.victor.bcnc.domain.repository;

import java.sql.Timestamp;
import java.util.Optional;

import com.victor.bcnc.domain.Price;

public interface PriceRepository {
    public Optional<Price> findByDateAndBrandIDAndProductID(Timestamp date, Integer brandID, Integer productID);
}
