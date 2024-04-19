package com.currencyexchange.controller;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES;
import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.RefreshMode.BEFORE_EACH_TEST_METHOD;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.currencyexchange.model.CurrencyExchangeRate;
import com.currencyexchange.model.ExchangeRateDTO;
import com.currencyexchange.repository.CurrencyRepository;
import com.currencyexchange.service.CurrencyService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureEmbeddedDatabase(type = POSTGRES, provider = ZONKY, refresh = BEFORE_EACH_TEST_METHOD)
class CurrencyControllerComponentTest {
  public static final String BASE_PATH = "/currency";
  @Autowired MockMvc mockMvc;

  @Autowired CurrencyService service;

  @Autowired CurrencyRepository repository;

  @Test
  void should_return_all_rates() throws Exception {
    saveData();

    var rates =
        new ObjectMapper()
            .readValue(
                mockMvc
                    .perform(get(BASE_PATH).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(
                        status().isOk()) // Check for the expected HTTP status code, e.g., 200 OK
                    .andReturn()
                    .getResponse()
                    .getContentAsString(),
                new TypeReference<List<CurrencyExchangeRate>>() {});

    assertEquals(3, rates.size());
  }

  @Test
  void should_return_specific_currency() throws Exception {
    var currency = "EUR";

    saveData();

    var rate =
        new ObjectMapper()
            .readValue(
                mockMvc
                    .perform(
                        get(BASE_PATH + "/" + currency).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(
                        status().isOk()) // Check for the expected HTTP status code, e.g., 200 OK
                    .andReturn()
                    .getResponse()
                    .getContentAsString(),
                new TypeReference<CurrencyExchangeRate>() {});

    assertEquals(1.2, rate.getRate());
    assertEquals(currency, rate.getCurrency());
  }

  @Test
  void should_save_custom_currency() throws Exception {
    var currency = "CCC";
    var rate = 100.00;
    var currencyDTO = buildExchangeRateDto(currency, rate);

    mockMvc
        .perform(
            post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(currencyDTO)))
        .andExpect(status().isCreated());

    var result = repository.findByCurrency("CCC").orElse(null);

    assertNotNull(result);
    assertEquals(rate, result.getRate());
    assertEquals(currency, result.getCurrency());
  }

  @Test
  void should_throw_error_when_currency_not_found() throws Exception {
    var currency = "XYZ";

    try {
      mockMvc
          .perform(get(BASE_PATH + "/" + currency).contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$.message").value("Currency XYZ not found"));
    } catch (Exception e) {
      Throwable rootCause = e.getCause();
      assertTrue(rootCause instanceof RuntimeException);
      assertEquals("Provided currency XYZ not found", rootCause.getMessage());
    }
  }

  private ExchangeRateDTO buildExchangeRateDto(String currency, Double rate) {
    return ExchangeRateDTO.builder().currency(currency).rate(rate).build();
  }

  private void saveData() {
    var currencies =
        Arrays.asList(
            CurrencyExchangeRate.builder().currency("USD").rate(1.0).build(),
            CurrencyExchangeRate.builder().currency("EUR").rate(1.2).build(),
            CurrencyExchangeRate.builder().currency("GBP").rate(1.4).build());

    repository.saveAll(currencies);
  }
}
