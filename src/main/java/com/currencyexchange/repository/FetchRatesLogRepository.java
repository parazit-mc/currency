package com.currencyexchange.repository;

import com.currencyexchange.model.CurrencyExchangeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FetchRatesLogRepository extends JpaRepository<CurrencyExchangeLog, Long> {}
