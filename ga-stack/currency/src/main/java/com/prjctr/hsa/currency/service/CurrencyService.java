package com.prjctr.hsa.currency.service;

import com.prjctr.hsa.currency.model.dto.CurrenctRateRespDto;

public interface CurrencyService {
    public CurrenctRateRespDto getRate(String sellCurrency, String buyCurrency);
}
