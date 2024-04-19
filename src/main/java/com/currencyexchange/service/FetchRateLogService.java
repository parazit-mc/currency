package com.currencyexchange.service;

import com.currencyexchange.model.CurrencyExchangeLog;
import com.currencyexchange.repository.FetchRatesLogRepository;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FetchRateLogService {
  @Autowired FetchRatesLogRepository repository;

  public void logCurrencyExchange(String currencyCode, Double rate) {
    CurrencyExchangeLog logEntry =
        CurrencyExchangeLog.builder()
            .currency(currencyCode)
            .rate(rate)
            .createdDate(Instant.now())
            .build();

    repository.save(logEntry);
  }
}
