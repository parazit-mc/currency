package com.currencyexchange.model;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyRateExchangeResponse {
  private Map<String, Double> data;
}
