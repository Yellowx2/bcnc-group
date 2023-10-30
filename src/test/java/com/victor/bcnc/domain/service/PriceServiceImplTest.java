package com.victor.bcnc.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import com.victor.bcnc.application.error.exceptions.NotFoundException;
import com.victor.bcnc.domain.Price;
import com.victor.bcnc.domain.repository.PriceRepository;
import com.victor.bcnc.utils.Stubs;

public class PriceServiceImplTest {

    private PriceRepository priceRepository;
    private PriceService priceService;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String date = LocalDateTime.now().format(formatter);
    private static final Integer productID = 1;
    private static final Integer brandID = 1;

    @Test
    public void dateAboveOneYearRangeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            priceService.getPrice(
                    LocalDateTime.now().plusYears(2).format(formatter),
                    productID, brandID);
        });
    }

    @Test
    public void dateBelowOneYearRangeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            priceService.getPrice(
                    LocalDateTime.now().minusYears(2).format(formatter),
                    productID, brandID);
        });
    }

    @Test
    public void productIDBelowZeroThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            priceService.getPrice(date, -1, brandID);
        });
    }

    @Test
    public void brandIDBelowZeroThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            priceService.getPrice(date, productID, -1);
        });
    }

    @Test
    public void repositoryReturnsEmpty() {
        var timestamp = Timestamp.valueOf(LocalDateTime.parse(date, formatter));
        Optional<Price> noPrice = Optional.empty();
        BDDMockito.given(priceRepository.findByDateAndBrandIDAndProductID(timestamp, brandID, productID))
                .willReturn(noPrice);
        assertThrows(NotFoundException.class, () -> {
            priceService.getPrice(date, productID, brandID);
        });
    }

    @Test
    public void repositoryReturnsPrice() throws IllegalArgumentException, NotFoundException {
        var timestamp = Timestamp.valueOf(LocalDateTime.parse(date, formatter));
        var want = Optional.of(Stubs.createPrice());
        BDDMockito.given(priceRepository.findByDateAndBrandIDAndProductID(timestamp, brandID, productID))
                .willReturn(want);

        assertEquals(want.get(), priceService.getPrice(date, productID, brandID));
    }

    @BeforeEach
    void setUp() {
        priceRepository = mock(PriceRepository.class);
        priceService = new PriceServiceImpl(priceRepository);
    }
}
