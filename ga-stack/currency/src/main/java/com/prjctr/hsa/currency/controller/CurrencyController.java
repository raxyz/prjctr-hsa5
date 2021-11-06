package com.prjctr.hsa.currency.controller;

import javax.servlet.http.HttpSession;

import com.prjctr.hsa.currency.model.GAEvent;
import com.prjctr.hsa.currency.model.GAEventType;
import com.prjctr.hsa.currency.model.dto.CurrenctRateRespDto;
import com.prjctr.hsa.currency.service.CurrencyService;
import com.prjctr.hsa.currency.service.GAService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/currency")
@AllArgsConstructor
@Slf4j
public class CurrencyController {
    private CurrencyService currencyService;
    private GAService gaService;
    
    @GetMapping
    @RequestMapping("/{ticker}")
    public ResponseEntity<CurrenctRateRespDto> getCurrencyRate(@PathVariable String ticker, HttpSession session) {
        sendPageViewEvent(session.getId(), ticker);

        var currencies = ticker.replaceAll(" ", "").split(":");
        var sellCurrency = currencies[0];
        var buyCurrency = currencies[1];
        log.info("sell/buy: {} : {}", sellCurrency, buyCurrency);
        var rate = currencyService.getRate(sellCurrency, buyCurrency);
        if (rate == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rate);
    }

    private void sendPageViewEvent(String sessionId, String ticker) {
        var gaEventBuiler = GAEvent.builder()
            .clientId(sessionId)
            .type(GAEventType.PAGEVIEW)
            .documentPath("/api/v1/currency")
            .documentTitle(ticker);

        gaService.sendTrackingInfo(gaEventBuiler);
    }
}
