package com.currencyexchange.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class CurrencyExchangeLogEntityTest {

  @Test
  void should_build_entity() {
    var id = 1L;
    var currency = "USD";
    var rate = 1.0;
    var createdDate = Instant.now();

    var logEntry = new CurrencyExchangeLog(id, currency, rate, createdDate);

    assertNotNull(logEntry);
    assertEquals(id, logEntry.getId());
    assertEquals(currency, logEntry.getCurrency());
    assertEquals(rate, logEntry.getRate());
    assertEquals(createdDate, logEntry.getCreatedDate());
  }
}
