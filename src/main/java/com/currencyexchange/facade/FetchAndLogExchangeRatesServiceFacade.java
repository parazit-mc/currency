package com.currencyexchange.facade;

import com.currencyexchange.model.CurrencyExchangeRate;
import com.currencyexchange.service.FetchRateLogService;
import com.currencyexchange.service.FetchRatesService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class FetchAndLogExchangeRatesServiceFacade {

  @Autowired FetchRatesService fetchService;

  @Autowired FetchRateLogService logService;

  @Transactional
  @Scheduled(fixedRate = 3600000)
  public void fetchAndSaveExchangeRates() {
    log.info("Service started");
    List<CurrencyExchangeRate> rates = fetchService.fetchAndSaveExchangeRates();
    rates.forEach(rate -> logService.logCurrencyExchange(rate.getCurrency(), rate.getRate()));
    log.info("Service finished");
  }
}
