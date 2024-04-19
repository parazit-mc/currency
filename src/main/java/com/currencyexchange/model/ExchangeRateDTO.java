package com.currencyexchange.model;

import lombok.Builder;

@Builder
public record ExchangeRateDTO(String currency, Double rate) {}
