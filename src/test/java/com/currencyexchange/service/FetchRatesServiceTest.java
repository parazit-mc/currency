package com.currencyexchange.service;

import static org.mockito.Mockito.*;

import com.currencyexchange.model.CurrencyExchangeRate;
import com.currencyexchange.model.CurrencyRateExchangeResponse;
import com.currencyexchange.repository.CurrencyRepository;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class FetchRatesServiceTest {
  @Mock private CurrencyRepository repository;

  @Mock private RestTemplate restTemplate;

  @InjectMocks private FetchRatesService service;

  @Value("${exchange.rate.api.url}")
  private String exchangeRateApiUrl;

  @Test
  void should_fetch_and_save_exchange_rates() {
    var exchangeResponse = new CurrencyRateExchangeResponse();
    exchangeResponse.setData(Map.of("USD", 1.0, "EUR", 2.0));
    var responseEntity = new ResponseEntity<>(exchangeResponse, HttpStatus.OK);

    when(restTemplate.exchange(
            eq(exchangeRateApiUrl),
            eq(HttpMethod.GET),
            any(),
            eq(CurrencyRateExchangeResponse.class)))
        .thenReturn(responseEntity);

    doNothing().when(repository).deleteAll();
    when(repository.saveAll(anyList()))
        .thenReturn(
            Arrays.asList(
                new CurrencyExchangeRate(1L, "USD", 1.0),
                new CurrencyExchangeRate(2L, "EUR", 2.0)));

    service.fetchAndSaveExchangeRates();

    verify(repository, times(1)).deleteAll();
    verify(repository, times(1)).saveAll(anyList());
    verify(restTemplate, times(1))
        .exchange(
            eq(exchangeRateApiUrl),
            eq(HttpMethod.GET),
            any(),
            eq(CurrencyRateExchangeResponse.class));
  }
}
