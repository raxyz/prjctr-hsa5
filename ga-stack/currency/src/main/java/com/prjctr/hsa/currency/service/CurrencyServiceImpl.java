package com.prjctr.hsa.currency.service;

import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.prjctr.hsa.currency.model.CurrencyRate;
import com.prjctr.hsa.currency.model.dto.CurrenctRateRespDto;
import com.prjctr.hsa.currency.model.dto.MonoCurrencyDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {
    private List<CurrencyRate> monoRates;
    private long cacheTime;
    private WebClient webClient;

    CurrencyServiceImpl(@Value("${webclient.currencyApiUrl}") String currencyApiUrl) {
        this.webClient = WebClient.builder()
            .baseUrl(currencyApiUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }
    
    public CurrenctRateRespDto getRate(String sellCurrency, String buyCurrency) {
        if (monoRates == null || cacheTime < System.currentTimeMillis()) {
            var cur = webClient.get()
                .retrieve()
                .bodyToFlux(MonoCurrencyDto.class)
                .collect(Collectors.toList())
                .share()
                .block();
            monoRates = convertMonoToRate(cur);
            cacheTime = System.currentTimeMillis() + 5 * 60 * 1000;
            log.info("Load rates from mono. Cache time: {}", new Date(cacheTime));
        }


        var exchangeRate = monoRates.stream()
            .filter(r -> sellCurrency.equals(r.getSell().getCurrencyCode()) && 
                            buyCurrency.equals(r.getBuy().getCurrencyCode()))
            .findFirst();
        
        
        if (exchangeRate.isPresent()) {
            var e = exchangeRate.get();
            return new CurrenctRateRespDto(e.getSell().getDisplayName(), e.getBuy().getDisplayName(), 
                e.getDate(), e.getRateSell(), e.getRateBuy());
        }


        return null;
    }
    
    private List<CurrencyRate> convertMonoToRate(List<MonoCurrencyDto> monoCurrencies) {
        return monoCurrencies.stream()
            .map(mc -> {
                var sellCurrency = getInstanceIso(mc.getCurrencyCodeA());
                var buyCurrency = getInstanceIso(mc.getCurrencyCodeB());

                return new CurrencyRate(sellCurrency, buyCurrency, 
                    mc.getDate(), mc.getRateSell(), mc.getRateBuy());
            })
            .collect(Collectors.toList());
    }

    private Currency getInstanceIso(int numericCode) {
        return Currency.getAvailableCurrencies().stream()
            .filter(c -> numericCode == c.getNumericCode())
            .findFirst()
            .get();
    }
}
