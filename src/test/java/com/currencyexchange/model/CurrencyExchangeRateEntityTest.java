package com.currencyexchange.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class CurrencyExchangeRateEntityTest {

  @Test
  void should_build_entity() {

    var id = 1L;
    var currency = "USD";
    var rateValue = 1.0;

    var rate = new CurrencyExchangeRate(id, currency, rateValue);

    assertNotNull(rate);
    assertEquals(id, rate.getId());
    assertEquals(currency, rate.getCurrency());
    assertEquals(rateValue, rate.getRate());
  }
}
