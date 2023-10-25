package com.victor.bcnc.application.rest;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.victor.bcnc.application.response.PriceResponse;
import com.victor.bcnc.domain.service.PriceService;
import com.victor.bcnc.infrastructure.config.JacksonConfiguration;
import com.victor.bcnc.utils.LocalDateTimeAfterMatcher;
import com.victor.bcnc.utils.Stubs;

@WebMvcTest(PriceController.class)
@Import(JacksonConfiguration.class)
public class PriceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PriceService priceService;

    private static ObjectMapper mapper;

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Test
    public void getPriceReturnsPrice() throws Exception {
        var price = Stubs.createPrice();
        var date = price.startDate().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));

        given(priceService.getPrice(date, price.productID(), price.brandID()))
                .willReturn(price);

        var want = new PriceResponse(price.brandID(), price.startDate(), price.endDate(), price.productID(),
                price.price(), price.curr());
        var wantBody = mapper.writeValueAsString(want);

        mvc.perform(get("/prices?date=" + date + "&brandID=" + price.brandID() + "&productID=" + price.productID()))
                .andExpect(status().isOk())
                .andExpect(content().json(wantBody));
    }

    @Test
    public void getPriceReturnsNullPrice() throws Exception {
        var price = Stubs.createPrice();
        var date = price.startDate().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));

        given(priceService.getPrice(date, price.productID(), price.brandID()))
                .willReturn(null);

        mvc.perform(get("/prices?date=" + date + "&brandID=" + price.brandID() + "&productID=" + price.productID()))
                .andExpect(status().isNotFound())
                .andExpect(result -> result.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void getPriceThrowsUnhandledException() throws Exception {
        var now = LocalDateTime.now();
        var price = Stubs.createPrice();
        var date = price.startDate().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        var message = "custom message";

        given(priceService.getPrice(date, price.productID(), price.brandID()))
                .willThrow(new RuntimeException(message));

        mvc.perform(get("/prices?date=" + date + "&brandID=" + price.brandID() + "&productID=" + price.productID()))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(jsonPath("$.message").value(message))
                .andExpect(jsonPath("$.timestamp")
                        .value(new LocalDateTimeAfterMatcher(now, DATE_TIME_FORMAT)));
    }

    @Test
    public void notFilledMandatoryQueryParamReturnsBadRequestError() throws Exception {
        var now = LocalDateTime.now();
        var price = Stubs.createPrice();
        var date = price.startDate().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));

        mvc.perform(get("/prices?date=" + date + "&brandID=" + price.brandID()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.timestamp")
                        .value(new LocalDateTimeAfterMatcher(now, DATE_TIME_FORMAT)));
    }

    @BeforeAll
    private static void setup() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        SimpleModule module = new SimpleModule();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        module.addSerializer(new LocalDateTimeSerializer(dateTimeFormatter));
        mapper.registerModule(module);
    }
}
