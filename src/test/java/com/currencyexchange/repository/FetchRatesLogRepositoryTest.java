package com.currencyexchange.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.currencyexchange.model.CurrencyExchangeLog;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FetchRatesLogRepositoryTest {

  @Mock private FetchRatesLogRepository repository;

  @Test
  void testFindAll() {
    var expectedLogs = getCurrencyExchangeLogs();

    when(repository.findAll()).thenReturn(expectedLogs);
    var result = repository.findAll();

    assertEquals(expectedLogs, result);
  }

  @Test
  void testFindById() {
    Long id = 1L;
    var expectedLog =
        CurrencyExchangeLog.builder().currency("USD").rate(1.0).createdDate(Instant.now()).build();

    when(repository.findById(id)).thenReturn(Optional.of(expectedLog));
    var result = repository.findById(id);

    assertEquals(Optional.of(expectedLog), result);
  }

  private List<CurrencyExchangeLog> getCurrencyExchangeLogs() {
    var log1 =
        CurrencyExchangeLog.builder().currency("USD").rate(1.0).createdDate(Instant.now()).build();
    var log2 =
        CurrencyExchangeLog.builder().currency("EUR").rate(2.0).createdDate(Instant.now()).build();

    var expectedLogs = Arrays.asList(log1, log2);
    return expectedLogs;
  }
}
