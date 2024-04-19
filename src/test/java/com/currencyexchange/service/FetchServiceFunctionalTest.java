package com.currencyexchange.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.currencyexchange.repository.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class FetchServiceFunctionalTest {

  @Value("${exchange.rate.api.url}")
  private String exchangeRateApiUrl;

  @Autowired private FetchRatesService service;

  @Autowired private CurrencyRepository repository;

  @Autowired RestTemplate restTemplate;

  private MockRestServiceServer mockServer;

  @BeforeEach
  public void init() {
    mockServer = MockRestServiceServer.createServer(restTemplate);
  }

  @Test
  void should_fetch_exchange_rates() {
    var responseBody =
        """
                  {
                  "data": {
                    "USD": 1.0,
                    "EUR": 2.0,
                    "GBP": 3.0
                  }
                }""";

    mockServer
        .expect(requestTo(exchangeRateApiUrl))
        .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

    var savedRates = service.fetchAndSaveExchangeRates();

    mockServer.verify();

    assertEquals(3, savedRates.size());

    var result = repository.findAll();
    assertEquals(3, result.size());

    assertTrue(result.stream().anyMatch(currency -> currency.getCurrency().equals("USD")));
    assertTrue(result.stream().anyMatch(currency -> currency.getCurrency().equals("EUR")));
    assertTrue(result.stream().anyMatch(currency -> currency.getCurrency().equals("GBP")));
  }
}
