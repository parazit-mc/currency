package com.currencyexchange.service;

import com.currencyexchange.mapper.CurrencyMapper;
import com.currencyexchange.model.CurrencyExchangeRate;
import com.currencyexchange.model.ExchangeRateDTO;
import com.currencyexchange.repository.CurrencyRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

  @Autowired CurrencyRepository repository;

  @Autowired CurrencyMapper mapper;

  public List<ExchangeRateDTO> getAllRates() {
    return repository.findAll().stream().map(mapper::mapToDto).toList();
  }

  public Optional<ExchangeRateDTO> getSingleRate(String currencyCode) {
    return repository.findByCurrency(currencyCode.toUpperCase()).map(mapper::mapToDto);
  }

  public ExchangeRateDTO saveRate(ExchangeRateDTO exchangeRateDTO) {
    CurrencyExchangeRate exchangeRate = mapper.map(exchangeRateDTO);
    repository.save(exchangeRate);
    return mapper.mapToDto(exchangeRate);
  }
}
