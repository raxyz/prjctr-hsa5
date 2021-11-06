package com.prjctr.hsa.currency.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GAEventTest {
    private static final String TRACKING_ID = "UA-123";

    @Test
    void testSimpleGAEntity() throws IllegalArgumentException, IllegalAccessException {
        var gaEvent = GAEvent.builder()
            .version("1")
            .trackingId(TRACKING_ID)
            .clientId("555")
            .type(GAEventType.EVENT)
            .build();
        var res = String.format("v=1&tid=%s&cid=555&t=event", TRACKING_ID);
        assertEquals(res, gaEvent.toRequestString());
    }
}
