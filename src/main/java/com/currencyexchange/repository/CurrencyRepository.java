package com.currencyexchange.repository;

import com.currencyexchange.model.CurrencyExchangeRate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyExchangeRate, Long> {
  Optional<CurrencyExchangeRate> findByCurrency(String currencyCode);
}
