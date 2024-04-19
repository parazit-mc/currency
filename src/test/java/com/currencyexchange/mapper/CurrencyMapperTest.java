package com.currencyexchange.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.currencyexchange.model.CurrencyExchangeRate;
import com.currencyexchange.model.ExchangeRateDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CurrencyMapperTest {
  @InjectMocks CurrencyMapperImpl mapper;

  @Test
  void should_map_entity_to_dto() {
    var entity = CurrencyExchangeRate.builder().currency("AAA").rate(1000.00).build();

    var dto = mapper.mapToDto(entity);

    assertEquals(entity.getCurrency(), dto.currency());
    assertEquals(entity.getRate(), dto.rate());
  }

  @Test
  void should_map_dto_to_entity() {
    var dto = ExchangeRateDTO.builder().currency("AAA").rate(1000.00).build();

    var result = mapper.map(dto);

    assertEquals(dto.currency(), result.getCurrency());
    assertEquals(dto.rate(), result.getRate());
  }
}
