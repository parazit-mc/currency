package com.currencyexchange.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.currencyexchange.model.CurrencyExchangeRate;
import com.currencyexchange.service.CurrencyService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CurrencyRepositoryTest {

  @Mock private CurrencyRepository repository;

  @InjectMocks private CurrencyService service;

  @Test
  void should_find_currency_by_code() {
    var currencyCode = "USD";
    var expectedRate = CurrencyExchangeRate.builder().currency(currencyCode).rate(1.0).build();

    when(repository.findByCurrency(currencyCode)).thenReturn(Optional.of(expectedRate));
    Optional<CurrencyExchangeRate> result = repository.findByCurrency(currencyCode);

    assertEquals(Optional.of(expectedRate), result);
  }
}
