package com.currencyexchange.api;

import com.currencyexchange.model.ExchangeRateDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface CurrencyApi {

  ResponseEntity<List<ExchangeRateDTO>> getAllRates();

  ResponseEntity<ExchangeRateDTO> getSingleRate(String currencyCode);

  ResponseEntity<ExchangeRateDTO> saveRate(ExchangeRateDTO exchangeRateDTO);
}
