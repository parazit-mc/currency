package com.currencyexchange.service;

import static org.mockito.Mockito.*;

import com.currencyexchange.repository.FetchRatesLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FetchRateLogServiceTest {
  @Mock private FetchRatesLogRepository repository;

  @InjectMocks private FetchRateLogService service;

  @Test
  void should_log_exchange_rates() {
    service.logCurrencyExchange("USD", 1.0);
    verify(repository, times(1)).save(any());
  }
}
