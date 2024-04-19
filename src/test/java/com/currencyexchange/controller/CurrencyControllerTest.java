package com.currencyexchange.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.currencyexchange.model.ExchangeRateDTO;
import com.currencyexchange.service.CurrencyService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class CurrencyControllerTest {
  @Mock private CurrencyService currencyService;

  @InjectMocks private CurrencyController currencyController;

  @Test
  void should_return_all_rates() {
    List<ExchangeRateDTO> rates = new ArrayList<>();
    rates.add(ExchangeRateDTO.builder().currency("AAA").rate(1.0).build());
    rates.add(ExchangeRateDTO.builder().currency("BBB").rate(2.0).build());

    when(currencyService.getAllRates()).thenReturn(rates);

    var responseEntity = currencyController.getAllRates();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(rates, responseEntity.getBody());
  }

  @Test
  void should_return_rate() {
    var currencyCode = "USD";
    ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO(currencyCode, 1.0);

    when(currencyService.getSingleRate(currencyCode)).thenReturn(Optional.of(exchangeRateDTO));

    var responseEntity = currencyController.getSingleRate(currencyCode);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(exchangeRateDTO, responseEntity.getBody());
  }

  @Test
  void should_save_custom_rate() {
    ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO("USD", 1.0);
    when(currencyService.saveRate(exchangeRateDTO)).thenReturn(exchangeRateDTO);

    var responseEntity = currencyController.saveRate(exchangeRateDTO);

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals(exchangeRateDTO, responseEntity.getBody());
  }

  @Test
  void should_throw_when_no_rate_found() {
    var currencyCode = "INVALID";
    when(currencyService.getSingleRate(currencyCode)).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> currencyController.getSingleRate(currencyCode));

    assertEquals("Provided currency INVALID not found", exception.getMessage());
  }
}
