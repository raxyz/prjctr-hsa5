package com.prjctr.hsa.currency.job;

import java.util.Random;

import com.prjctr.hsa.currency.model.GAEvent;
import com.prjctr.hsa.currency.model.GAEventType;
import com.prjctr.hsa.currency.service.CurrencyService;
import com.prjctr.hsa.currency.service.GAService;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Component
@EnableAsync
public class SendRateCron {
    private CurrencyService service;
    private GAService gaService;
 
    @Async
    @Scheduled(fixedRate = 1000 * 5)
    public void sendUsdUahRate() {
        var rate = service.getRate("USD", "UAH");

        var randomUserId = new Random().nextInt(5 - 1 + 1) + 1;

        var gaEventBuiler = GAEvent.builder()
            .clientId("cron-job-" + randomUserId)
            .type(GAEventType.EVENT)
            .eventCategory("rate-job")
            .eventAction("get")
            .eventLabel("USD:UAH " + rate.getRateSell());
        
        gaService.sendTrackingInfo(gaEventBuiler);

        log.info("Event sent: {}", gaEventBuiler);
    }
}
