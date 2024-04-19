package com.currencyexchange.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.currencyexchange.mapper.CurrencyMapper;
import com.currencyexchange.model.CurrencyExchangeRate;
import com.currencyexchange.model.ExchangeRateDTO;
import com.currencyexchange.repository.CurrencyRepository;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {
  @Mock private CurrencyRepository repository;

  @Mock private CurrencyMapper mapper;

  @InjectMocks private CurrencyService service;

  @Test
  void should_return_all_rates() {
    var rates =
        Arrays.asList(
            new CurrencyExchangeRate(1L, "USD", 1.0),
            new CurrencyExchangeRate(2L, "EUR", 0.82),
            new CurrencyExchangeRate(3L, "GBP", 0.72));

    var expected = rates.stream().map(mapper::mapToDto).collect(Collectors.toList());

    when(repository.findAll()).thenReturn(rates);

    var result = service.getAllRates();

    assertThat(result).hasSize(3).containsExactlyElementsOf(expected);
  }

  @Test
  void should_return_single_rate() {
    var currencyCode = "USD";
    var rate = new CurrencyExchangeRate(1L, currencyCode, 1.0);
    var expected = new ExchangeRateDTO(currencyCode, 1.0);
    when(repository.findByCurrency(currencyCode.toUpperCase())).thenReturn(Optional.of(rate));
    when(mapper.mapToDto(rate)).thenReturn(expected);

    var actual = service.getSingleRate(currencyCode);

    assertTrue(actual.isPresent());
    assertEquals(expected.currency(), actual.get().currency());
    assertEquals(expected.rate(), actual.get().rate());
  }

  @Test
  void should_return_empty_when_no_currency_found() {
    var currencyCode = "XYZ";
    when(repository.findByCurrency(currencyCode)).thenReturn(Optional.empty());
    var result = service.getSingleRate(currencyCode);
    assertTrue(result.isEmpty());
  }

  @Test
  void testSaveRate() {
    var currency = "USD";
    var rate = 1.0;
    var dto = new ExchangeRateDTO(currency, rate);
    var exchangeRate = new CurrencyExchangeRate(null, currency, rate);

    when(mapper.map(dto)).thenReturn(exchangeRate);
    when(repository.save(any())).thenReturn(exchangeRate);
    when(mapper.mapToDto(exchangeRate)).thenReturn(dto);
    assertEquals(dto, service.saveRate(dto));
  }
}
