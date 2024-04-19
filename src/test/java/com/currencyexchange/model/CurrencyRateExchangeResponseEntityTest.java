package com.currencyexchange.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CurrencyRateExchangeResponseEntityTest {

  @Test
  void should_build_entity() {
    Map<String, Double> testData = new HashMap<>();
    testData.put("USD", 1.0);
    testData.put("EUR", 0.85);

    var response = new CurrencyRateExchangeResponse();
    response.setData(testData);

    assertNotNull(response);
    assertEquals(testData, response.getData());
  }
}
