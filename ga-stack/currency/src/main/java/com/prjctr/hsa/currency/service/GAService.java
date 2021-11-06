package com.prjctr.hsa.currency.service;

import com.prjctr.hsa.currency.model.GAEvent;

public interface GAService {
    public String sendTrackingInfo(GAEvent.GAEventBuilder event);
}
