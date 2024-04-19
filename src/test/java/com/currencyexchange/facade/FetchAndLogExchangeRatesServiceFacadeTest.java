package com.currencyexchange.facade;

import static org.mockito.Mockito.*;

import com.currencyexchange.model.CurrencyExchangeRate;
import com.currencyexchange.service.FetchRateLogService;
import com.currencyexchange.service.FetchRatesService;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FetchAndLogExchangeRatesServiceFacadeTest {
  @Mock private FetchRatesService fetchService;

  @Mock private FetchRateLogService logService;

  @InjectMocks private FetchAndLogExchangeRatesServiceFacade decorator;

  @Test
  void should_fetch_save_and_log_exchange_rates() {
    when(fetchService.fetchAndSaveExchangeRates())
        .thenReturn(
            Arrays.asList(
                new CurrencyExchangeRate(1L, "USD", 1.0),
                new CurrencyExchangeRate(2L, "EUR", 2.0)));

    decorator.fetchAndSaveExchangeRates();

    verify(fetchService, times(1)).fetchAndSaveExchangeRates(); // Verify fetchService method call
    verify(logService, times(1)).logCurrencyExchange("USD", 1.0); // Verify logService method call
    verify(logService, times(1)).logCurrencyExchange("EUR", 2.0); // Verify logService method call
  }
}
