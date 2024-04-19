package com.currencyexchange.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ExchangeRateDTOTest {

  @Test
  void should_build_dto() {
    var currency = "USD";
    var rate = 1.0;
    var dto = new ExchangeRateDTO(currency, rate);

    assertEquals(currency, dto.currency());
    assertEquals(rate, dto.rate());
  }
}
