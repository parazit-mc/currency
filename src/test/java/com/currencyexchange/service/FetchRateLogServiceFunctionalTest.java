package com.currencyexchange.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import com.currencyexchange.model.CurrencyExchangeLog;
import com.currencyexchange.repository.FetchRatesLogRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class FetchRateLogServiceFunctionalTest {

  @Autowired private FetchRateLogService service;

  @MockBean private FetchRatesLogRepository repository;

  @Test
  void should_log_exchange_rates() {
    var currencyCode = "USD";
    var rate = 1.0;

    service.logCurrencyExchange(currencyCode, rate);

    ArgumentCaptor<CurrencyExchangeLog> captor = ArgumentCaptor.forClass(CurrencyExchangeLog.class);
    verify(repository).save(captor.capture());

    var result = captor.getValue();
    assertEquals(currencyCode, result.getCurrency());
    assertEquals(rate, result.getRate());
    assertNotNull(result.getCreatedDate());
  }
}
