package com.currencyexchange.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "currency_rate")
public class CurrencyExchangeRate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "currency_code", nullable = false)
  private String currency;

  @Column(name = "exchange_rate", nullable = false)
  private Double rate;
}
