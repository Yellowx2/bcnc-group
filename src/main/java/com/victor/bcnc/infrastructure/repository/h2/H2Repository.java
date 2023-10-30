package com.victor.bcnc.infrastructure.repository.h2;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.victor.bcnc.domain.Price;
import com.victor.bcnc.domain.repository.PriceRepository;

/**
 * H2Repository
 */
@Repository
public class H2Repository implements PriceRepository {

    @Override
    public Optional<Price> findByDateAndBrandIDAndProductID(Timestamp date, Integer brandID, Integer productID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByDateAndBrandIDAndProductID'");
    }

}