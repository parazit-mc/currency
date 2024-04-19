package com.currencyexchange.service;

import com.currencyexchange.model.CurrencyExchangeRate;
import com.currencyexchange.model.CurrencyRateExchangeResponse;
import com.currencyexchange.repository.CurrencyRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class FetchRatesService {

  @Value("${exchange.rate.api.url}")
  private String exchangeRateApiUrl;

  @Autowired CurrencyRepository repository;

  @Autowired RestTemplate restTemplate;

  public List<CurrencyExchangeRate> fetchAndSaveExchangeRates() {
    repository.deleteAll();
    List<CurrencyExchangeRate> rates = new ArrayList<>();

    var response =
        restTemplate.exchange(
            exchangeRateApiUrl, HttpMethod.GET, null, CurrencyRateExchangeResponse.class);

    if (response.getStatusCode() == HttpStatus.OK) {
      CurrencyRateExchangeResponse exchangeResponse = response.getBody();
      rates =
          exchangeResponse.getData().entrySet().stream()
              .map(entry -> buildCurrencyExchangeRate(entry.getKey(), entry.getValue()))
              .toList();

      repository.saveAll(rates);
      log.info("Data saved");
    }
    return rates;
  }

  private CurrencyExchangeRate buildCurrencyExchangeRate(String currencyCode, Double rate) {
    return CurrencyExchangeRate.builder().currency(currencyCode).rate(rate).build();
  }
}
