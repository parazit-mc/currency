package com.currencyexchange.mapper;

import com.currencyexchange.model.CurrencyExchangeRate;
import com.currencyexchange.model.ExchangeRateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

  @Mapping(target = "id", ignore = true)
  CurrencyExchangeRate map(ExchangeRateDTO exchangeRateDTO);

  ExchangeRateDTO mapToDto(CurrencyExchangeRate currencyExchangeRate);
}
