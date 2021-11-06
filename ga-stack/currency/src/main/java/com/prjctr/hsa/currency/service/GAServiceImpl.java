package com.prjctr.hsa.currency.service;

import com.prjctr.hsa.currency.model.GAEvent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GAServiceImpl implements GAService {
    private static final String GA_VERSION = "1";
    private final String TRACKING_ID;
    private WebClient webClient;

    
    GAServiceImpl(@Value("${ga.tracking_id}") String trackingId,
                    @Value("${webclient.gaApiUrl}") String gaApiUrl) {
        this.TRACKING_ID = trackingId;
        this.webClient = WebClient.builder()
            .baseUrl(gaApiUrl)
            .defaultHeader(HttpHeaders.HOST, "www.google-analytics.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .build();
    }

    public String sendTrackingInfo(GAEvent.GAEventBuilder eventBuilder) {
        eventBuilder
            .trackingId(TRACKING_ID)
            .version(GA_VERSION);
        return webClient.post()
            .bodyValue(eventBuilder.build().toRequestString())
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
    
}
