package com.victor.bcnc.domain.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Currency;

import org.springframework.stereotype.Service;

import com.victor.bcnc.domain.Price;

@Service
public class PriceServiceImpl implements PriceService {

    @Override
    public Price getPrice(String date, Integer productID, Integer brandID) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'getPrice'");
        // var localDate = LocalDateTime.parse(date,
        // DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return new Price(1, LocalDateTime.now(), LocalDateTime.now().plus(Period.ofDays(1)), 1, 1, 0,
                new BigDecimal(10.0), Currency.getInstance("EUR"));
    }

}
