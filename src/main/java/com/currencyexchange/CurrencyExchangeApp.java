package com.currencyexchange;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(
    info =
        @Info(
            title = "Currency Exchange API",
            version = "1.0",
            description = "API for managing currency exchange rates"))
public class CurrencyExchangeApp {

  public static void main(String[] args) {
    SpringApplication.run(CurrencyExchangeApp.class, args);
  }
}
