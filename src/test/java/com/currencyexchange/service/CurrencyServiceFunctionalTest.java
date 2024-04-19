package com.currencyexchange.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.currencyexchange.model.CurrencyExchangeRate;
import com.currencyexchange.model.ExchangeRateDTO;
import com.currencyexchange.repository.CurrencyRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CurrencyServiceFunctionalTest {
  @Autowired private CurrencyService service;

  @Autowired private CurrencyRepository repository;

  @BeforeEach
  void setUp() {
    repository.deleteAll();
  }

  @Test
  void should_save_rate() {
    var currencyCode = "USD";
    var rate = 1.0;
    var dto = ExchangeRateDTO.builder().currency(currencyCode).rate(rate).build();

    service.saveRate(dto);
    var result = repository.findByCurrency(currencyCode);
    assertEquals(1, repository.findAll().size());
    assertTrue(result.isPresent());
    assertEquals(currencyCode, result.get().getCurrency());
    assertEquals(rate, result.get().getRate());
  }

  @Test
  void should_return_all_rates() {
    repository.saveAll(buildRates());
    var result = service.getAllRates();
    assertEquals(3, result.size());
    assertTrue(result.stream().anyMatch(c -> c.currency().equals("USD")));
    assertTrue(result.stream().anyMatch(c -> c.currency().equals("EUR")));
    assertTrue(result.stream().anyMatch(c -> c.currency().equals("GBP")));
  }

  @Test
  void should_return_single_rate() {
    var currencyCode = "USD";
    repository.saveAll(buildRates());
    var result = service.getSingleRate("USD");
    assertTrue(result.isPresent());
    assertEquals(currencyCode, result.get().currency());
  }

  private List<CurrencyExchangeRate> buildRates() {
    var rate1 = new CurrencyExchangeRate(1L, "USD", 1.0);
    var rate2 = new CurrencyExchangeRate(2L, "EUR", 1.2);
    var rate3 = new CurrencyExchangeRate(3L, "GBP", 1.4);

    return Arrays.asList(rate1, rate2, rate3);
  }
}
