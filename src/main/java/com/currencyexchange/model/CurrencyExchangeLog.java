package com.currencyexchange.model;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "currency_log")
public class CurrencyExchangeLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "currency_code", nullable = false)
  private String currency;

  @Column(name = "exchange_rate", nullable = false)
  private Double rate;

  @CreatedDate
  @Column(name = "changed_at", updatable = false)
  private Instant createdDate;
}
