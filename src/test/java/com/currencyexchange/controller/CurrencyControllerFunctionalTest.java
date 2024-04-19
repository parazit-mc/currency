package com.currencyexchange.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.currencyexchange.model.ExchangeRateDTO;
import com.currencyexchange.service.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CurrencyController.class)
@AutoConfigureMockMvc
class CurrencyControllerFunctionalTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @MockBean private CurrencyService service;

  @Test
  void should_return_all_rates() throws Exception {

    var rate1 = new ExchangeRateDTO("USD", 1.0);
    var rate2 = new ExchangeRateDTO("EUR", 1.2);
    var rate3 = new ExchangeRateDTO("GBP", 1.4);

    var rates = Arrays.asList(rate1, rate2, rate3);

    when(service.getAllRates()).thenReturn(rates);

    mockMvc
        .perform(get("/currency").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.size()").value(rates.size()))
        .andExpect(jsonPath("$[0].currency").value(rate1.currency()))
        .andExpect(jsonPath("$[0].rate").value(rate1.rate()))
        .andExpect(jsonPath("$[1].currency").value(rate2.currency()))
        .andExpect(jsonPath("$[1].rate").value(rate2.rate()))
        .andExpect(jsonPath("$[2].currency").value(rate3.currency()))
        .andExpect(jsonPath("$[2].rate").value(rate3.rate()));
  }

  @Test
  void should_return_single_rate() throws Exception {
    var currencyCode = "USD";
    var rate = new ExchangeRateDTO(currencyCode, 1.0);
    when(service.getSingleRate(currencyCode)).thenReturn(Optional.of(rate));

    mockMvc
        .perform(
            get("/currency/{currencyCode}", currencyCode).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.currency").value(rate.currency()))
        .andExpect(jsonPath("$.rate").value(rate.rate()));
  }

  @Test
  void should_save_custom_exchange_rate_to_database() throws Exception {
    var exchangeRateDTO = new ExchangeRateDTO("AAA", 100.00);

    when(service.saveRate(any(ExchangeRateDTO.class))).thenReturn(exchangeRateDTO);

    mockMvc
        .perform(
            post("/currency")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exchangeRateDTO)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.currency").value(exchangeRateDTO.currency()))
        .andExpect(jsonPath("$.rate").value(exchangeRateDTO.rate()));
  }

  @Test
  void should_throw_error_when_currency_not_found() throws Exception {
    var currencyCode = "UNKNOWN";
    when(service.getSingleRate(currencyCode)).thenReturn(Optional.empty());

    try {
      mockMvc.perform(
          get("/currency/{currencyCode}", currencyCode).contentType(MediaType.APPLICATION_JSON));
      fail("Expected RuntimeException");
    } catch (Exception e) {
      assertTrue(e.getCause() instanceof RuntimeException);
      assertEquals("Provided currency UNKNOWN not found", e.getCause().getMessage());
    }
  }
}
